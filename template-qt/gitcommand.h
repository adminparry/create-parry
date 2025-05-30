#ifndef GITCOMMAND_H
#define GITCOMMAND_H

#include <QString>
#include <QStringList>
#include <QProcess>

class GitCommand : public QObject
{
    Q_OBJECT

public:
    explicit GitCommand(const QString &workingDir, QObject *parent = nullptr);

    // Repository operations
    bool clone(const QString &url, const QString &path);
    bool init(const QString &path);
    QString status();
    bool add(const QStringList &files);
    bool commit(const QString &message);
    bool push(const QString &remote = "origin", const QString &branch = QString());
    bool pull(const QString &remote = "origin", const QString &branch = QString());

    // Branch operations
    QStringList branches();
    bool createBranch(const QString &name);
    bool checkoutBranch(const QString &name);
    bool deleteBranch(const QString &name);
    bool mergeBranch(const QString &branch);
    bool rebaseBranch(const QString &branch);

    // Tag operations
    QStringList tags();
    bool createTag(const QString &name, const QString &message = QString());
    bool deleteTag(const QString &name);
    bool pushTag(const QString &name, const QString &remote = "origin");

    // Remote operations
    QStringList remotes();
    bool addRemote(const QString &name, const QString &url);
    bool removeRemote(const QString &name);
    QString getRemoteUrl(const QString &name);

    // Stash operations
    bool stashSave(const QString &message = QString());
    bool stashPop();
    bool stashApply(const QString &stash = "stash@{0}");
    bool stashDrop(const QString &stash = "stash@{0}");
    QStringList stashList();

    // Log operations
    QString log(int maxCount = -1);
    QString showCommit(const QString &hash);
    QString diff(const QString &file = QString());
    QString blame(const QString &file);

    // Reset operations
    bool reset(const QString &file);
    bool resetHard(const QString &commit = "HEAD");
    bool resetSoft(const QString &commit = "HEAD^");

    // Submodule operations
    bool submoduleAdd(const QString &url, const QString &path);
    bool submoduleUpdate(bool init = true);
    bool submoduleSync();

    // Error handling
    QString lastError() const;
    int lastExitCode() const;

private:
    bool runGit(const QStringList &arguments, QString *output = nullptr);
    void setWorkingDirectory(const QString &path);

    QString workingDirectory;
    QString errorMessage;
    int exitCode;
    QProcess process;
};

#endif // GITCOMMAND_H 