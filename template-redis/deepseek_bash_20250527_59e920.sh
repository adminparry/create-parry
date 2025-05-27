#!/bin/bash
# Redis单机伪集群安装脚本（3主3从）

REDIS_VERSION="7.0.11"
INSTALL_DIR="/opt/redis"
CLUSTER_PORTS=(7000 7001 7002 7003 7004 7005)  # 6个节点端口
CLUSTER_NODES=()  # 用于存储节点信息

# 安装依赖
sudo apt-get update
sudo apt-get install -y build-essential tcl

# 下载并编译Redis
wget https://download.redis.io/releases/redis-${REDIS_VERSION}.tar.gz
tar xzf redis-${REDIS_VERSION}.tar.gz
cd redis-${REDIS_VERSION}
make -j$(nproc)
sudo make install

# 创建集群目录
sudo mkdir -p ${INSTALL_DIR}
sudo chown -R $USER ${INSTALL_DIR}

# 配置每个节点
for PORT in "${CLUSTER_PORTS[@]}"; do
  NODE_DIR="${INSTALL_DIR}/${PORT}"
  mkdir -p ${NODE_DIR}
  
  # 创建配置文件
  cat > ${NODE_DIR}/redis.conf <<EOF
port ${PORT}
cluster-enabled yes
cluster-config-file nodes-${PORT}.conf
cluster-node-timeout 5000
appendonly yes
daemonize yes
logfile ${NODE_DIR}/redis.log
dir ${NODE_DIR}
EOF

  # 启动Redis实例
  redis-server ${NODE_DIR}/redis.conf
  CLUSTER_NODES+=("127.0.0.1:${PORT}")
done

# 等待所有节点启动
sleep 3

# 创建集群 (3主3从)
echo "正在创建Redis集群..."
redis-cli --cluster create ${CLUSTER_NODES[*]} --cluster-replicas 1 --cluster-yes

# 验证集群状态
echo "集群状态:"
redis-cli -p ${CLUSTER_PORTS[0]} cluster nodes | head -10
echo "集群信息:"
redis-cli -p ${CLUSTER_PORTS[0]} cluster info

echo "Redis伪集群搭建完成，运行在以下端口: ${CLUSTER_PORTS[*]}"