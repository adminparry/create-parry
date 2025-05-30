#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QTreeView>
#include <QTableView>
#include <QListView>
#include <QSplitter>
#include <QTextEdit>
#include <QPushButton>
#include <QLineEdit>
#include <QLabel>
#include "repositorymodel.h"
#include "commithistorymodel.h"

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void openRepository();
    void cloneRepository();
    void commitChanges();
    void pushChanges();
    void pullChanges();
    void branchCreate();
    void branchCheckout();
    void refreshStatus();

private:
    void setupUI();
    void setupMenus();
    void setupToolbar();
    void setupConnections();
    void updateStatusBar(const QString &message);

    Ui::MainWindow *ui;
    
    // UI Components
    QSplitter *mainSplitter;
    QTreeView *repoTreeView;
    QTableView *commitHistoryView;
    QListView *changesListView;
    QTextEdit *diffTextEdit;
    
    // Models
    RepositoryModel *repoModel;
    CommitHistoryModel *commitModel;
    
    // Current repository path
    QString currentRepoPath;
}; 