#!/bin/bash
# Kafka分布式集群安装脚本

# 集群节点配置
NODES=("node1" "node2" "node3")  # 替换为实际主机名或IP
ZOOKEEPER_NODES=("node1" "node2" "node3")  # Zookeeper节点
KAFKA_VERSION="3.4.0"
SCALA_VERSION="2.13"
INSTALL_DIR="/opt/kafka"
ZOOKEEPER_PORT=2181
KAFKA_PORT=9092

# 在每个节点上执行安装
for NODE in "${NODES[@]}"; do
  echo "====== 在节点 ${NODE} 上安装 ======"
  
  ssh ${NODE} << EOF
    # 安装Java
    sudo apt-get update
    sudo apt-get install -y openjdk-11-jdk
    
    # 下载Kafka
    wget https://downloads.apache.org/kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz
    tar -xzf kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz
    sudo mv kafka_${SCALA_VERSION}-${KAFKA_VERSION} ${INSTALL_DIR}
    
    # 配置Zookeeper
    echo "tickTime=2000" > ${INSTALL_DIR}/config/zookeeper.properties
    echo "initLimit=10" >> ${INSTALL_DIR}/config/zookeeper.properties
    echo "syncLimit=5" >> ${INSTALL_DIR}/config/zookeeper.properties
    echo "dataDir=/var/lib/zookeeper" >> ${INSTALL_DIR}/config/zookeeper.properties
    echo "clientPort=${ZOOKEEPER_PORT}" >> ${INSTALL_DIR}/config/zookeeper.properties
    
    # 添加Zookeeper集群配置
    ZOOKEEPER_ID=\$(echo ${ZOOKEEPER_NODES[@]} | tr ' ' '\n' | grep -n \$HOSTNAME | cut -d: -f1)
    if [ ! -z "\$ZOOKEEPER_ID" ]; then
      echo "server.\${ZOOKEEPER_ID}=\${HOSTNAME}:2888:3888" >> ${INSTALL_DIR}/config/zookeeper.properties
      sudo mkdir -p /var/lib/zookeeper
      echo "\${ZOOKEEPER_ID}" | sudo tee /var/lib/zookeeper/myid > /dev/null
    fi
    
    # 配置Kafka
    BROKER_ID=\$(echo ${NODES[@]} | tr ' ' '\n' | grep -n \$HOSTNAME | cut -d: -f1)
    sed -i "s/broker.id=0/broker.id=\${BROKER_ID}/" ${INSTALL_DIR}/config/server.properties
    sed -i "s#log.dirs=/tmp/kafka-logs#log.dirs=${INSTALL_DIR}/kafka-logs#" ${INSTALL_DIR}/config/server.properties
    sed -i "s/zookeeper.connect=localhost:2181/zookeeper.connect=$(IFS=,; echo "${ZOOKEEPER_NODES[*]/%/:${ZOOKEEPER_PORT}}")/" ${INSTALL_DIR}/config/server.properties
    
    # 配置监听器
    sed -i "s/^#listeners=PLAINTEXT:\/\/:9092/listeners=PLAINTEXT:\/\/:${KAFKA_PORT}/" ${INSTALL_DIR}/config/server.properties
    sed -i "s/^#advertised.listeners=PLAINTEXT:\/\/your.host.name:9092/advertised.listeners=PLAINTEXT:\/\/\${HOSTNAME}:${KAFKA_PORT}/" ${INSTALL_DIR}/config/server.properties
    
    # 创建日志目录
    mkdir -p ${INSTALL_DIR}/kafka-logs
EOF
done

# 启动Zookeeper集群
for NODE in "${ZOOKEEPER_NODES[@]}"; do
  echo "在节点 ${NODE} 上启动Zookeeper"
  ssh ${NODE} "${INSTALL_DIR}/bin/zookeeper-server-start.sh -daemon ${INSTALL_DIR}/config/zookeeper.properties"
done

# 等待Zookeeper集群就绪
sleep 10

# 启动Kafka集群
for NODE in "${NODES[@]}"; do
  echo "在节点 ${NODE} 上启动Kafka"
  ssh ${NODE} "${INSTALL_DIR}/bin/kafka-server-start.sh -daemon ${INSTALL_DIR}/config/server.properties"
done

echo "Kafka集群安装完成"
echo "Zookeeper节点: ${ZOOKEEPER_NODES[*]}"
echo "Kafka节点: ${NODES[*]}"