#!/bin/bash
# Redis集群扩容脚本

NEW_NODE="node7"  # 新节点地址
NEW_PORT=7000     # 新节点端口
EXISTING_NODE="node1:7000"  # 现有集群节点

# 在新节点上安装Redis
ssh ${NEW_NODE} <<EOF
  sudo apt-get update
  sudo apt-get install -y redis-server
  sudo mkdir -p /opt/redis/${NEW_PORT}
  
  cat > /opt/redis/${NEW_PORT}/redis.conf <<EOL
port ${NEW_PORT}
cluster-enabled yes
cluster-config-file nodes-${NEW_PORT}.conf
cluster-node-timeout 5000
appendonly yes
daemonize yes
logfile /opt/redis/${NEW_PORT}/redis.log
dir /opt/redis/${NEW_PORT}
bind 0.0.0.0
EOL

  redis-server /opt/redis/${NEW_PORT}/redis.conf
EOF

# 添加新节点到集群
redis-cli --cluster add-node ${NEW_NODE}:${NEW_PORT} ${EXISTING_NODE}

# 重新分片数据到新节点
redis-cli --cluster reshard ${EXISTING_NODE} \
  --cluster-from $(redis-cli -c -h ${EXISTING_NODE%:*} -p ${EXISTING_NODE#*:} cluster nodes | grep master | awk '{print $1}' | tr '\n' ',') \
  --cluster-to $(redis-cli -c -h ${NEW_NODE} -p ${NEW_PORT} cluster nodes | grep myself | awk '{print $1}') \
  --cluster-slots 1000 \
  --cluster-yes

echo "新节点 ${NEW_NODE}:${NEW_PORT} 已添加到集群"