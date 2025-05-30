#!/bin/bash

# 应用配置
APP_NAME="production-app"
JAR_NAME="app.jar"
APP_HOME="/opt/$APP_NAME"
LOG_DIR="$APP_HOME/logs"
PID_FILE="$APP_HOME/$APP_NAME.pid"

# 根据CPU核心数设置并行线程
CPU_CORES=$(nproc)
PARALLEL_GC_THREADS=$(( CPU_CORES > 8 ? 8 : CPU_CORES ))
CONC_GC_THREADS=$(( PARALLEL_GC_THREADS / 4 ))

# JVM 基础配置
JAVA_OPTS="-server -Dfile.encoding=UTF-8 -Duser.timezone=GMT+08"
JAVA_OPTS="$JAVA_OPTS -Djava.security.egd=file:/dev/./urandom"

# 内存配置（自动检测容器或物理机内存）
if [ -f "/sys/fs/cgroup/memory/memory.limit_in_bytes" ]; then
    # 容器环境
    CONTAINER_MEM=$(cat /sys/fs/cgroup/memory/memory.limit_in_bytes)
    MAX_MEM=$(( CONTAINER_MEM / 1024 / 1024 * 70 / 100 ))m
    JAVA_OPTS="$JAVA_OPTS -XX:+UseContainerSupport -Xms$MAX_MEM -Xmx$MAX_MEM"
else
    # 物理机环境
    JAVA_OPTS="$JAVA_OPTS -Xms8g -Xmx8g"
fi

# G1 GC 配置
JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC"
JAVA_OPTS="$JAVA_OPTS -XX:MaxGCPauseMillis=200"
JAVA_OPTS="$JAVA_OPTS -XX:InitiatingHeapOccupancyPercent=45"
JAVA_OPTS="$JAVA_OPTS -XX:ParallelGCThreads=$PARALLEL_GC_THREADS"
JAVA_OPTS="$JAVA_OPTS -XX:ConcGCThreads=$CONC_GC_THREADS"
JAVA_OPTS="$JAVA_OPTS -XX:G1HeapRegionSize=4m"

# 监控和诊断
JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError"
JAVA_OPTS="$JAVA_OPTS -XX:HeapDumpPath=$LOG_DIR/heapdump.hprof"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
JAVA_OPTS="$JAVA_OPTS -Xloggc:$LOG_DIR/gc.log -XX:+UseGCLogFileRotation"
JAVA_OPTS="$JAVA_OPTS -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=50M"

# 性能优化
JAVA_OPTS="$JAVA_OPTS -XX:+AlwaysPreTouch -XX:AutoBoxCacheMax=20000"
JAVA_OPTS="$JAVA_OPTS -XX:-OmitStackTraceInFastThrow"
JAVA_OPTS="$JAVA_OPTS -XX:+ExplicitGCInvokesConcurrent"
JAVA_OPTS="$JAVA_OPTS -XX:+ParallelRefProcEnabled"
JAVA_OPTS="$JAVA_OPTS -XX:+PerfDisableSharedMem"

# 启动应用
start() {
    if [ -f "$PID_FILE" ]; then
        echo "Application is already running!"
        exit 1
    fi

    echo "Starting $APP_NAME..."
    mkdir -p $LOG_DIR

    nohup java $JAVA_OPTS -jar $APP_HOME/$JAR_NAME >> $LOG_DIR/app.log 2>&1 &

    echo $! > $PID_FILE
    echo "Started $APP_NAME with PID $!"
}


# 停止应用
stop() {
    if [ ! -f "$PID_FILE" ]; then
        echo "Application is not running!"
        exit 1
    fi

    echo "Stopping $APP_NAME..."
    PID=$(cat $PID_FILE)
    kill $PID
    rm -f $PID_FILE
    echo "Stopped $APP_NAME"
}

case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        sleep 5
        start
        ;;
    *)
        echo "Usage: $0 {start|stop|restart}"
        exit 1
        ;;
esac