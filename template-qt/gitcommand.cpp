#include "gitcommand.h"
#include <QDir>

GitCommand::GitCommand(const QString &workingDir, QObject *parent)
    : QObject(parent), workingDirectory(workingDir), exitCode(0)
{
    process.setWorkingDirectory(workingDirectory);
}

bool GitCommand::clone(const QString &url, const QString &path)
{
    QStringList args;
    args << "clone" << url << path;
    return runGit(args);
}

bool GitCommand::init(const QString &path)
{
    setWorkingDirectory(path);
    return runGit(QStringList() << "init");
}

QString GitCommand::status()
{
    QString output;
    runGit(QStringList() << "status", &output);
    return output;
}

bool GitCommand::add(const QStringList &files)
{
    QStringList args;
    args << "add";
    args.append(files);
    return runGit(args);
}

bool GitCommand::commit(const QString &message)
{
    return runGit(QStringList() << "commit" << "-m" << message);
}

bool GitCommand::push(const QString &remote, const QString &branch)
{
    QStringList args;
    args << "push";
    if (!remote.isEmpty())
        args << remote;
    if (!branch.isEmpty())
        args << branch;
    return runGit(args);
}

bool GitCommand::pull(const QString &remote, const QString &branch)
{
    QStringList args;
    args << "pull";
    if (!remote.isEmpty())
        args << remote;
    if (!branch.isEmpty())
        args << branch;
    return runGit(args);
}

QStringList GitCommand::branches()
{
    QString output;
    QStringList branches;
    if (runGit(QStringList() << "branch" << "--list", &output)) {
        const QStringList lines = output.split('\n', Qt::SkipEmptyParts);
        for (const QString &line : lines) {
            QString branch = line.mid(2); // Remove "* " or "  " prefix
            if (!branch.isEmpty())
                branches << branch;
        }
    }
    return branches;
}

bool GitCommand::createBranch(const QString &name)
{
    return runGit(QStringList() << "branch" << name);
}

bool GitCommand::checkoutBranch(const QString &name)
{
    return runGit(QStringList() << "checkout" << name);
}

bool GitCommand::deleteBranch(const QString &name)
{
    return runGit(QStringList() << "branch" << "-d" << name);
}

QString GitCommand::log(int maxCount)
{
    QStringList args;
    args << "log" << "--pretty=format:%H%n%h%n%an%n%ae%n%at%n%s%n%P%n%D";
    if (maxCount > 0)
        args << QString("-n%1").arg(maxCount);
    
    QString output;
    runGit(args, &output);
    return output;
}

QString GitCommand::showCommit(const QString &hash)
{
    QString output;
    runGit(QStringList() << "show" << hash, &output);
    return output;
}

QString GitCommand::diff(const QString &file)
{
    QStringList args;
    args << "diff";
    if (!file.isEmpty())
        args << file;
    
    QString output;
    runGit(args, &output);
    return output;
}

QString GitCommand::lastError() const
{
    return errorMessage;
}

int GitCommand::lastExitCode() const
{
    return exitCode;
}

bool GitCommand::runGit(const QStringList &arguments, QString *output)
{
    process.start("git", arguments);
    process.waitForFinished();
    
    exitCode = process.exitCode();
    errorMessage = QString::fromUtf8(process.readAllStandardError());
    
    if (output)
        *output = QString::fromUtf8(process.readAllStandardOutput());
    
    return exitCode == 0;
}

void GitCommand::setWorkingDirectory(const QString &path)
{
    workingDirectory = path;
    process.setWorkingDirectory(path);
}

bool GitCommand::mergeBranch(const QString &branch)
{
    return runGit(QStringList() << "merge" << branch);
}

bool GitCommand::rebaseBranch(const QString &branch)
{
    return runGit(QStringList() << "rebase" << branch);
}

QStringList GitCommand::tags()
{
    QString output;
    QStringList tagList;
    if (runGit(QStringList() << "tag" << "--list", &output)) {
        tagList = output.split('\n', Qt::SkipEmptyParts);
    }
    return tagList;
}

bool GitCommand::createTag(const QString &name, const QString &message)
{
    QStringList args;
    args << "tag";
    if (!message.isEmpty()) {
        args << "-a" << name << "-m" << message;
    } else {
        args << name;
    }
    return runGit(args);
}

bool GitCommand::deleteTag(const QString &name)
{
    return runGit(QStringList() << "tag" << "-d" << name);
}

bool GitCommand::pushTag(const QString &name, const QString &remote)
{
    return runGit(QStringList() << "push" << remote << name);
}

QStringList GitCommand::remotes()
{
    QString output;
    QStringList remoteList;
    if (runGit(QStringList() << "remote", &output)) {
        remoteList = output.split('\n', Qt::SkipEmptyParts);
    }
    return remoteList;
}

bool GitCommand::addRemote(const QString &name, const QString &url)
{
    return runGit(QStringList() << "remote" << "add" << name << url);
}

bool GitCommand::removeRemote(const QString &name)
{
    return runGit(QStringList() << "remote" << "remove" << name);
}

QString GitCommand::getRemoteUrl(const QString &name)
{
    QString output;
    runGit(QStringList() << "remote" << "get-url" << name, &output);
    return output.trimmed();
}

bool GitCommand::stashSave(const QString &message)
{
    QStringList args;
    args << "stash" << "push";
    if (!message.isEmpty()) {
        args << "-m" << message;
    }
    return runGit(args);
}

bool GitCommand::stashPop()
{
    return runGit(QStringList() << "stash" << "pop");
}

bool GitCommand::stashApply(const QString &stash)
{
    return runGit(QStringList() << "stash" << "apply" << stash);
}

bool GitCommand::stashDrop(const QString &stash)
{
    return runGit(QStringList() << "stash" << "drop" << stash);
}

QStringList GitCommand::stashList()
{
    QString output;
    QStringList stashList;
    if (runGit(QStringList() << "stash" << "list", &output)) {
        stashList = output.split('\n', Qt::SkipEmptyParts);
    }
    return stashList;
}

QString GitCommand::blame(const QString &file)
{
    QString output;
    runGit(QStringList() << "blame" << file, &output);
    return output;
}

bool GitCommand::reset(const QString &file)
{
    return runGit(QStringList() << "reset" << "HEAD" << file);
}

bool GitCommand::resetHard(const QString &commit)
{
    return runGit(QStringList() << "reset" << "--hard" << commit);
}

bool GitCommand::resetSoft(const QString &commit)
{
    return runGit(QStringList() << "reset" << "--soft" << commit);
}

bool GitCommand::submoduleAdd(const QString &url, const QString &path)
{
    return runGit(QStringList() << "submodule" << "add" << url << path);
}

bool GitCommand::submoduleUpdate(bool init)
{
    QStringList args;
    args << "submodule" << "update";
    if (init) {
        args << "--init";
    }
    return runGit(args);
}

bool GitCommand::submoduleSync()
{
    return runGit(QStringList() << "submodule" << "sync");
} 