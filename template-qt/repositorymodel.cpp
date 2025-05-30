#include "repositorymodel.h"
#include "gitcommand.h"
#include <QDir>
#include <QFileInfo>
#include <QProcess>
#include <QIcon>

class RepositoryItem {
public:
    explicit RepositoryItem(const QString &name, const QString &path, RepositoryItem *parent = nullptr)
        : itemName(name), itemPath(path), parentItem(parent) {}
    ~RepositoryItem() { qDeleteAll(childItems); }

    void appendChild(RepositoryItem *child) { childItems.append(child); }
    RepositoryItem *child(int row) { return childItems.value(row); }
    int childCount() const { return childItems.count(); }
    QString name() const { return itemName; }
    QString path() const { return itemPath; }
    RepositoryItem *parent() { return parentItem; }
    int row() const {
        if (parentItem)
            return parentItem->childItems.indexOf(const_cast<RepositoryItem*>(this));
        return 0;
    }

private:
    QString itemName;
    QString itemPath;
    QList<RepositoryItem*> childItems;
    RepositoryItem *parentItem;
};

RepositoryModel::RepositoryModel(QObject *parent)
    : QAbstractItemModel(parent), rootItem(nullptr)
{
    connect(&fileWatcher, &QFileSystemWatcher::directoryChanged,
            this, &RepositoryModel::handleDirectoryChanged);
    connect(&fileWatcher, &QFileSystemWatcher::fileChanged,
            this, &RepositoryModel::handleFileChanged);
}

RepositoryModel::~RepositoryModel()
{
    delete rootItem;
}

QModelIndex RepositoryModel::index(int row, int column, const QModelIndex &parent) const
{
    if (!hasIndex(row, column, parent))
        return QModelIndex();

    RepositoryItem *parentItem;
    if (!parent.isValid())
        parentItem = rootItem;
    else
        parentItem = static_cast<RepositoryItem*>(parent.internalPointer());

    RepositoryItem *childItem = parentItem->child(row);
    if (childItem)
        return createIndex(row, column, childItem);
    return QModelIndex();
}

QModelIndex RepositoryModel::parent(const QModelIndex &index) const
{
    if (!index.isValid())
        return QModelIndex();

    RepositoryItem *childItem = static_cast<RepositoryItem*>(index.internalPointer());
    RepositoryItem *parentItem = childItem->parent();

    if (parentItem == rootItem)
        return QModelIndex();

    return createIndex(parentItem->row(), 0, parentItem);
}

int RepositoryModel::rowCount(const QModelIndex &parent) const
{
    if (parent.column() > 0)
        return 0;

    RepositoryItem *parentItem;
    if (!parent.isValid())
        parentItem = rootItem;
    else
        parentItem = static_cast<RepositoryItem*>(parent.internalPointer());

    return parentItem ? parentItem->childCount() : 0;
}

int RepositoryModel::columnCount(const QModelIndex &parent) const
{
    return 1;
}

QVariant RepositoryModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    RepositoryItem *item = static_cast<RepositoryItem*>(index.internalPointer());

    if (role == Qt::DisplayRole)
        return item->name();
    else if (role == Qt::UserRole)
        return item->path();
    else if (role == Qt::DecorationRole) {
        // Add icons based on item type
        QString name = item->name();
        if (name == "Repository")
            return QIcon(":/icons/repository.png");
        else if (name == "Branches")
            return QIcon(":/icons/branch.png");
        else if (name == "Tags")
            return QIcon(":/icons/tag.png");
        else if (name == "Remotes")
            return QIcon(":/icons/remote.png");
        else if (name == "Working Copy")
            return QIcon(":/icons/folder.png");
        else {
            QFileInfo info(item->path());
            return info.isDir() ? QIcon(":/icons/folder.png") : QIcon(":/icons/file.png");
        }
    }

    return QVariant();
}

void RepositoryModel::refresh(const QString &path)
{
    beginResetModel();
    clearModel();
    loadRepository(path);
    endResetModel();

    // Update file watcher
    if (!fileWatcher.directories().isEmpty())
        fileWatcher.removePaths(fileWatcher.directories());
    if (!fileWatcher.files().isEmpty())
        fileWatcher.removePaths(fileWatcher.files());

    fileWatcher.addPath(path);
    updateStatus();
}

QString RepositoryModel::repositoryPath() const
{
    return repoPath;
}

void RepositoryModel::handleDirectoryChanged(const QString &path)
{
    refresh(repoPath);
}

void RepositoryModel::handleFileChanged(const QString &path)
{
    refresh(repoPath);
}

void RepositoryModel::loadRepository(const QString &path)
{
    repoPath = path;
    rootItem = new RepositoryItem("Repository", path);

    // Create basic structure
    auto workingCopyItem = new RepositoryItem("Working Copy", path, rootItem);
    auto branchesItem = new RepositoryItem("Branches", path, rootItem);
    auto tagsItem = new RepositoryItem("Tags", path, rootItem);
    auto remotesItem = new RepositoryItem("Remotes", path, rootItem);

    rootItem->appendChild(workingCopyItem);
    rootItem->appendChild(branchesItem);
    rootItem->appendChild(tagsItem);
    rootItem->appendChild(remotesItem);

    // Load working copy files
    QDir dir(path);
    QFileInfoList entries = dir.entryInfoList(QDir::AllEntries | QDir::NoDotAndDotDot);
    for (const QFileInfo &info : entries) {
        if (info.fileName() != ".git") {
            auto item = new RepositoryItem(info.fileName(), info.filePath(), workingCopyItem);
            workingCopyItem->appendChild(item);
        }
    }

    updateBranches();
}

void RepositoryModel::clearModel()
{
    delete rootItem;
    rootItem = nullptr;
}

void RepositoryModel::updateBranches()
{
    if (!rootItem)
        return;

    // Find the branches item
    RepositoryItem *branchesItem = nullptr;
    for (int i = 0; i < rootItem->childCount(); ++i) {
        if (rootItem->child(i)->name() == "Branches") {
            branchesItem = rootItem->child(i);
            break;
        }
    }

    if (!branchesItem)
        return;

    // Clear existing branches
    while (branchesItem->childCount() > 0) {
        delete branchesItem->child(0);
    }

    // Get branches using GitCommand
    GitCommand git(repoPath);
    QStringList branches = git.branches();

    // Add branches to the model
    for (const QString &branch : branches) {
        auto item = new RepositoryItem(branch, repoPath, branchesItem);
        branchesItem->appendChild(item);
    }
}

void RepositoryModel::updateStatus()
{
    if (!rootItem)
        return;

    // Find the working copy item
    RepositoryItem *workingCopyItem = nullptr;
    for (int i = 0; i < rootItem->childCount(); ++i) {
        if (rootItem->child(i)->name() == "Working Copy") {
            workingCopyItem = rootItem->child(i);
            break;
        }
    }

    if (!workingCopyItem)
        return;

    // Get status using GitCommand
    GitCommand git(repoPath);
    QString status = git.status();

    // Parse status and update working copy item
    // This is a basic implementation that could be enhanced
    QStringList lines = status.split('\n', Qt::SkipEmptyParts);
    for (const QString &line : lines) {
        if (line.startsWith("modified:") || 
            line.startsWith("new file:") ||
            line.startsWith("deleted:")) {
            QString fileName = line.section(':', 1).trimmed();
            bool found = false;
            
            // Check if file is already in the model
            for (int i = 0; i < workingCopyItem->childCount(); ++i) {
                if (workingCopyItem->child(i)->name() == fileName) {
                    found = true;
                    break;
                }
            }
            
            // Add new files to the model
            if (!found) {
                QString filePath = QDir(repoPath).filePath(fileName);
                auto item = new RepositoryItem(fileName, filePath, workingCopyItem);
                workingCopyItem->appendChild(item);
            }
        }
    }
} 