#!/bin/bash
# Kafka单机伪集群安装脚本

# 配置参数
KAFKA_VERSION="3.4.0"
SCALA_VERSION="2.13"
INSTALL_DIR="/opt/kafka"
CLUSTER_SIZE=3  # 伪集群broker数量
ZOOKEEPER_PORT=2181
KAFKA_PORTS=(9092 9093 9094)  # 每个broker的端口

# 安装Java
sudo apt-get update
sudo apt-get install -y openjdk-11-jdk

# 下载Kafka
wget https://downloads.apache.org/kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz
tar -xzf kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz
mv kafka_${SCALA_VERSION}-${KAFKA_VERSION} ${INSTALL_DIR}

# 配置伪集群
for ((i=0; i<$CLUSTER_SIZE; i++)); do
  BROKER_ID=$((i+1))
  PORT=${KAFKA_PORTS[$i]}
  LOG_DIR="${INSTALL_DIR}/kafka-logs-${BROKER_ID}"
  
  # 创建配置文件
  cp ${INSTALL_DIR}/config/server.properties ${INSTALL_DIR}/config/server-${BROKER_ID}.properties
  
  # 修改配置
  sed -i "s/broker.id=0/broker.id=${BROKER_ID}/" ${INSTALL_DIR}/config/server-${BROKER_ID}.properties
  sed -i "s#log.dirs=/tmp/kafka-logs#log.dirs=${LOG_DIR}#" ${INSTALL_DIR}/config/server-${BROKER_ID}.properties
  sed -i "s/port=9092/port=${PORT}/" ${INSTALL_DIR}/config/server-${BROKER_ID}.properties
  sed -i "s/zookeeper.connect=localhost:2181/zookeeper.connect=localhost:${ZOOKEEPER_PORT}/" ${INSTALL_DIR}/config/server-${BROKER_ID}.properties
  
  # 添加监听器
  echo "listeners=PLAINTEXT://:${PORT}" >> ${INSTALL_DIR}/config/server-${BROKER_ID}.properties
  echo "advertised.listeners=PLAINTEXT://localhost:${PORT}" >> ${INSTALL_DIR}/config/server-${BROKER_ID}.properties
  
  # 创建日志目录
  mkdir -p ${LOG_DIR}
done

# 启动Zookeeper
${INSTALL_DIR}/bin/zookeeper-server-start.sh -daemon ${INSTALL_DIR}/config/zookeeper.properties

# 启动Kafka brokers
for ((i=0; i<$CLUSTER_SIZE; i++)); do
  BROKER_ID=$((i+1))
  ${INSTALL_DIR}/bin/kafka-server-start.sh -daemon ${INSTALL_DIR}/config/server-${BROKER_ID}.properties
done

echo "Kafka伪集群启动完成，broker端口: ${KAFKA_PORTS[*]}"