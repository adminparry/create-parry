#ifndef COMMITHISTORYMODEL_H
#define COMMITHISTORYMODEL_H

#include <QAbstractTableModel>
#include <QList>
#include <QDateTime>

struct CommitInfo {
    QString hash;
    QString shortHash;
    QString author;
    QString email;
    QString message;
    QDateTime dateTime;
    QStringList parents;
    QStringList branches;
    QStringList tags;
};

class CommitHistoryModel : public QAbstractTableModel
{
    Q_OBJECT

public:
    enum Column {
        Graph,
        Hash,
        Author,
        Date,
        Message,
        ColumnCount
    };

    explicit CommitHistoryModel(QObject *parent = nullptr);

    // Required QAbstractTableModel implementation
    int rowCount(const QModelIndex &parent = QModelIndex()) const override;
    int columnCount(const QModelIndex &parent = QModelIndex()) const override;
    QVariant data(const QModelIndex &index, int role = Qt::DisplayRole) const override;
    QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;

    // Repository operations
    void refresh(const QString &path);
    CommitInfo commitInfo(const QModelIndex &index) const;

private:
    void loadCommits(const QString &path);
    void parseCommitLog(const QString &log);
    void clearModel();

    QList<CommitInfo> commits;
    QString repoPath;
}; 