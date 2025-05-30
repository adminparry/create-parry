#ifndef REPOSITORYMODEL_H
#define REPOSITORYMODEL_H

#include <QAbstractItemModel>
#include <QFileSystemWatcher>

class RepositoryItem;

class RepositoryModel : public QAbstractItemModel
{
    Q_OBJECT

public:
    explicit RepositoryModel(QObject *parent = nullptr);
    ~RepositoryModel();

    // Required QAbstractItemModel implementation
    QModelIndex index(int row, int column, const QModelIndex &parent = QModelIndex()) const override;
    QModelIndex parent(const QModelIndex &index) const override;
    int rowCount(const QModelIndex &parent = QModelIndex()) const override;
    int columnCount(const QModelIndex &parent = QModelIndex()) const override;
    QVariant data(const QModelIndex &index, int role = Qt::DisplayRole) const override;

    // Repository operations
    void refresh(const QString &path);
    QString repositoryPath() const;

private slots:
    void handleDirectoryChanged(const QString &path);
    void handleFileChanged(const QString &path);

private:
    void loadRepository(const QString &path);
    void clearModel();
    void updateBranches();
    void updateStatus();

    RepositoryItem *rootItem;
    QString repoPath;
    QFileSystemWatcher fileWatcher;
}; 