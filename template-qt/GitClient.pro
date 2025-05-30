QT       += core gui widgets

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

CONFIG += c++11

TARGET = GitClient
TEMPLATE = app

SOURCES += \
    main.cpp \
    mainwindow.cpp \
    repositorymodel.cpp \
    commithistorymodel.cpp \
    gitcommand.cpp

HEADERS += \
    mainwindow.h \
    repositorymodel.h \
    commithistorymodel.h \
    gitcommand.h

FORMS += \
    mainwindow.ui

# Default rules for deployment.
qnx: target.path = /tmp/$${TARGET}/bin
else: unix:!android: target.path = /opt/$${TARGET}/bin
!isEmpty(target.path): INSTALLS += target

RESOURCES += \
    resources.qrc 