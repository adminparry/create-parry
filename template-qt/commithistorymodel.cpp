#include "commithistorymodel.h"
#include "gitcommand.h"
#include <QProcess>
#include <QDateTime>
#include <QColor>

CommitHistoryModel::CommitHistoryModel(QObject *parent)
    : QAbstractTableModel(parent)
{
}

int CommitHistoryModel::rowCount(const QModelIndex &parent) const
{
    if (parent.isValid())
        return 0;
    return commits.count();
}

int CommitHistoryModel::columnCount(const QModelIndex &parent) const
{
    if (parent.isValid())
        return 0;
    return ColumnCount;
}

QVariant CommitHistoryModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= commits.count())
        return QVariant();

    const CommitInfo &commit = commits.at(index.row());

    if (role == Qt::DisplayRole) {
        switch (index.column()) {
            case Graph:
                return QString(); // Will be implemented with graph drawing
            case Hash:
                return commit.shortHash;
            case Author:
                return commit.author;
            case Date:
                return commit.dateTime.toString("yyyy-MM-dd HH:mm:ss");
            case Message:
                return commit.message;
        }
    }
    else if (role == Qt::ToolTipRole) {
        switch (index.column()) {
            case Hash:
                return commit.hash;
            case Author:
                return QString("%1 <%2>").arg(commit.author, commit.email);
            case Message:
                return commit.message;
        }
    }
    else if (role == Qt::BackgroundRole) {
        // Alternate row colors
        if (index.row() % 2)
            return QColor(245, 245, 245);
    }

    return QVariant();
}

QVariant CommitHistoryModel::headerData(int section, Qt::Orientation orientation, int role) const
{
    if (orientation == Qt::Horizontal && role == Qt::DisplayRole) {
        switch (section) {
            case Graph:
                return tr("Graph");
            case Hash:
                return tr("Hash");
            case Author:
                return tr("Author");
            case Date:
                return tr("Date");
            case Message:
                return tr("Message");
        }
    }
    return QVariant();
}

void CommitHistoryModel::refresh(const QString &path)
{
    beginResetModel();
    repoPath = path;
    loadCommits(path);
    endResetModel();
}

CommitInfo CommitHistoryModel::commitInfo(const QModelIndex &index) const
{
    if (!index.isValid() || index.row() >= commits.count())
        return CommitInfo();
    return commits.at(index.row());
}

void CommitHistoryModel::loadCommits(const QString &path)
{
    commits.clear();
    
    GitCommand git(path);
    QString log = git.log();
    parseCommitLog(log);
}

void CommitHistoryModel::parseCommitLog(const QString &log)
{
    QStringList entries = log.split('\n', Qt::SkipEmptyParts);
    
    for (int i = 0; i < entries.size(); i += 8) { // 8 lines per commit
        if (i + 7 >= entries.size())
            break;
            
        CommitInfo commit;
        commit.hash = entries[i];
        commit.shortHash = entries[i + 1];
        commit.author = entries[i + 2];
        commit.email = entries[i + 3];
        commit.dateTime = QDateTime::fromSecsSinceEpoch(entries[i + 4].toLongLong());
        commit.message = entries[i + 5];
        
        // Parse parent hashes
        if (!entries[i + 6].isEmpty()) {
            commit.parents = entries[i + 6].split(' ', Qt::SkipEmptyParts);
        }
        
        // Parse branches and tags
        if (!entries[i + 7].isEmpty()) {
            QStringList refs = entries[i + 7].split(',', Qt::SkipEmptyParts);
            for (QString &ref : refs) {
                ref = ref.trimmed();
                if (ref.startsWith("HEAD -> "))
                    ref = ref.mid(8);
                    
                if (ref.startsWith("tag: ")) {
                    commit.tags.append(ref.mid(5));
                } else {
                    commit.branches.append(ref);
                }
            }
        }
        
        commits.append(commit);
    }
}

void CommitHistoryModel::clearModel()
{
    beginResetModel();
    commits.clear();
    endResetModel();
} 