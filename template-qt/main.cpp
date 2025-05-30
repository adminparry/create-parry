#include "mainwindow.h"
#include <QApplication>
#include <QCommandLineParser>
#include <QCommandLineOption>

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);
    app.setApplicationName("GitClient");
    app.setApplicationVersion("1.0.0");

    // Command line parser
    QCommandLineParser parser;
    parser.setApplicationDescription("A Git client similar to SourceTree");
    parser.addHelpOption();
    parser.addVersionOption();

    // Add option to open repository directly
    QCommandLineOption repoOption(QStringList() << "r" << "repository",
                                "Open repository at <path>.",
                                "path");
    parser.addOption(repoOption);

    parser.process(app);

    MainWindow window;
    window.show();

    // If repository path is provided, open it
    if (parser.isSet(repoOption)) {
        QString repoPath = parser.value(repoOption);
        QMetaObject::invokeMethod(&window, "openRepository",
                                Qt::QueuedConnection,
                                Q_ARG(QString, repoPath));
    }

    return app.exec();
} 