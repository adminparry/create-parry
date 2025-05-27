#!/bin/bash
# Redis分布式集群安装脚本（3主3从）

REDIS_VERSION="7.0.11"
INSTALL_DIR="/opt/redis"
NODES=("node1" "node2" "node3" "node4" "node5" "node6")  # 替换为实际节点IP或主机名
PORTS=(7000 7000 7000 7000 7000 7000)  # 每个节点的端口
CLUSTER_NODES=()  # 用于存储节点信息

# 在每个节点上安装Redis
for i in "${!NODES[@]}"; do
  NODE=${NODES[$i]}
  PORT=${PORTS[$i]}
  
  echo "====== 在节点 ${NODE} 上安装Redis ======"
  
  ssh ${NODE} <<EOF
    # 安装依赖
    sudo apt-get update
    sudo apt-get install -y build-essential tcl
    
    # 下载并编译Redis
    wget https://download.redis.io/releases/redis-${REDIS_VERSION}.tar.gz
    tar xzf redis-${REDIS_VERSION}.tar.gz
    cd redis-${REDIS_VERSION}
    make -j\$(nproc)
    sudo make install
    
    # 创建节点目录
    sudo mkdir -p ${INSTALL_DIR}/${PORT}
    sudo chown -R \$USER ${INSTALL_DIR}
    
    # 创建配置文件
    cat > ${INSTALL_DIR}/${PORT}/redis.conf <<EOL
port ${PORT}
cluster-enabled yes
cluster-config-file nodes-${PORT}.conf
cluster-node-timeout 5000
appendonly yes
daemonize yes
logfile ${INSTALL_DIR}/${PORT}/redis.log
dir ${INSTALL_DIR}/${PORT}
bind 0.0.0.0
EOL
    
    # 启动Redis
    redis-server ${INSTALL_DIR}/${PORT}/redis.conf
EOF

  CLUSTER_NODES+=("${NODE}:${PORT}")
done

# 等待所有节点启动
sleep 5

# 创建集群 (3主3从)
echo "正在创建Redis集群..."
FIRST_NODE=${NODES[0]}:${PORTS[0]}
redis-cli --cluster create ${CLUSTER_NODES[*]} --cluster-replicas 1 --cluster-yes

# 验证集群状态
echo "集群状态:"
redis-cli -h ${NODES[0]} -p ${PORTS[0]} cluster nodes | head -10
echo "集群信息:"
redis-cli -h ${NODES[0]} -p ${PORTS[0]} cluster info

echo "