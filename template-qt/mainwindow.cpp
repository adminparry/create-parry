#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QMenuBar>
#include <QToolBar>
#include <QStatusBar>
#include <QFileDialog>
#include <QMessageBox>
#include <QVBoxLayout>
#include <QInputDialog>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    setupUI();
    setupMenus();
    setupToolbar();
    setupConnections();
    
    setWindowTitle(tr("Git Client"));
    setMinimumSize(1024, 768);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::setupUI()
{
    // Create main splitter
    mainSplitter = new QSplitter(Qt::Horizontal, this);
    setCentralWidget(mainSplitter);

    // Left side - Repository tree
    repoTreeView = new QTreeView(mainSplitter);
    repoModel = new RepositoryModel(this);
    repoTreeView->setModel(repoModel);
    repoTreeView->setHeaderHidden(true);
    mainSplitter->addWidget(repoTreeView);

    // Right side container
    QWidget *rightWidget = new QWidget(mainSplitter);
    QVBoxLayout *rightLayout = new QVBoxLayout(rightWidget);
    mainSplitter->addWidget(rightWidget);

    // Commit history view
    commitHistoryView = new QTableView(rightWidget);
    commitModel = new CommitHistoryModel(this);
    commitHistoryView->setModel(commitModel);
    rightLayout->addWidget(commitHistoryView);

    // Changes list and diff viewer splitter
    QSplitter *changesSplitter = new QSplitter(Qt::Vertical, rightWidget);
    rightLayout->addWidget(changesSplitter);

    // Changes list
    changesListView = new QListView(changesSplitter);
    changesSplitter->addWidget(changesListView);

    // Diff viewer
    diffTextEdit = new QTextEdit(changesSplitter);
    diffTextEdit->setReadOnly(true);
    diffTextEdit->setFont(QFont("Courier New", 10));
    changesSplitter->addWidget(diffTextEdit);

    // Set splitter sizes
    mainSplitter->setStretchFactor(0, 1);
    mainSplitter->setStretchFactor(1, 3);
}

void MainWindow::setupMenus()
{
    // File menu
    QMenu *fileMenu = menuBar()->addMenu(tr("&File"));
    fileMenu->addAction(tr("&Open Repository..."), this, &MainWindow::openRepository);
    fileMenu->addAction(tr("&Clone Repository..."), this, &MainWindow::cloneRepository);
    fileMenu->addSeparator();
    fileMenu->addAction(tr("E&xit"), this, &QWidget::close);

    // Repository menu
    QMenu *repoMenu = menuBar()->addMenu(tr("&Repository"));
    repoMenu->addAction(tr("&Commit"), this, &MainWindow::commitChanges);
    repoMenu->addAction(tr("&Push"), this, &MainWindow::pushChanges);
    repoMenu->addAction(tr("P&ull"), this, &MainWindow::pullChanges);
    repoMenu->addSeparator();
    repoMenu->addAction(tr("&Refresh"), this, &MainWindow::refreshStatus);

    // Branch menu
    QMenu *branchMenu = menuBar()->addMenu(tr("&Branch"));
    branchMenu->addAction(tr("&New Branch..."), this, &MainWindow::branchCreate);
    branchMenu->addAction(tr("&Checkout Branch..."), this, &MainWindow::branchCheckout);
}

void MainWindow::setupToolbar()
{
    QToolBar *toolbar = addToolBar(tr("Main Toolbar"));
    toolbar->addAction(QIcon::fromTheme("document-open"), tr("Open"), this, &MainWindow::openRepository);
    toolbar->addAction(QIcon::fromTheme("document-save"), tr("Commit"), this, &MainWindow::commitChanges);
    toolbar->addAction(QIcon::fromTheme("network-transmit"), tr("Push"), this, &MainWindow::pushChanges);
    toolbar->addAction(QIcon::fromTheme("network-receive"), tr("Pull"), this, &MainWindow::pullChanges);
    toolbar->addSeparator();
    toolbar->addAction(QIcon::fromTheme("view-refresh"), tr("Refresh"), this, &MainWindow::refreshStatus);
}

void MainWindow::setupConnections()
{
    // Connect signals and slots for views
    connect(repoTreeView, &QTreeView::clicked, this, [this](const QModelIndex &index) {
        // Handle repository tree item selection
    });

    connect(commitHistoryView, &QTableView::clicked, this, [this](const QModelIndex &index) {
        // Handle commit selection
    });

    connect(changesListView, &QListView::clicked, this, [this](const QModelIndex &index) {
        // Handle changed file selection
    });
}

void MainWindow::openRepository()
{
    QString dir = QFileDialog::getExistingDirectory(this, tr("Open Repository"),
                                                  QDir::homePath(),
                                                  QFileDialog::ShowDirsOnly);
    if (!dir.isEmpty()) {
        currentRepoPath = dir;
        refreshStatus();
        updateStatusBar(tr("Opened repository: %1").arg(dir));
    }
}

void MainWindow::cloneRepository()
{
    QString url = QInputDialog::getText(this, tr("Clone Repository"),
                                      tr("Repository URL:"));
    if (!url.isEmpty()) {
        QString dir = QFileDialog::getExistingDirectory(this, tr("Clone to"),
                                                      QDir::homePath(),
                                                      QFileDialog::ShowDirsOnly);
        if (!dir.isEmpty()) {
            // Implement repository cloning
            updateStatusBar(tr("Cloning repository..."));
        }
    }
}

void MainWindow::commitChanges()
{
    QString message = QInputDialog::getMultiLineText(this, tr("Commit Changes"),
                                                   tr("Commit message:"));
    if (!message.isEmpty()) {
        // Implement commit
        updateStatusBar(tr("Committing changes..."));
    }
}

void MainWindow::pushChanges()
{
    // Implement push
    updateStatusBar(tr("Pushing changes..."));
}

void MainWindow::pullChanges()
{
    // Implement pull
    updateStatusBar(tr("Pulling changes..."));
}

void MainWindow::branchCreate()
{
    QString name = QInputDialog::getText(this, tr("Create Branch"),
                                       tr("Branch name:"));
    if (!name.isEmpty()) {
        // Implement branch creation
        updateStatusBar(tr("Creating branch: %1").arg(name));
    }
}

void MainWindow::branchCheckout()
{
    QStringList branches; // Get list of branches
    QString branch = QInputDialog::getItem(this, tr("Checkout Branch"),
                                         tr("Select branch:"), branches);
    if (!branch.isEmpty()) {
        // Implement branch checkout
        updateStatusBar(tr("Checking out branch: %1").arg(branch));
    }
}

void MainWindow::refreshStatus()
{
    if (!currentRepoPath.isEmpty()) {
        // Refresh repository status
        repoModel->refresh(currentRepoPath);
        commitModel->refresh(currentRepoPath);
        updateStatusBar(tr("Repository status refreshed"));
    }
}

void MainWindow::updateStatusBar(const QString &message)
{
    statusBar()->showMessage(message, 3000);
} 