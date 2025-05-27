# 查看集群节点信息
redis-cli -c -p 7000 cluster nodes

# 检查集群状态
redis-cli --cluster check 127.0.0.1:7000

# 添加新节点（主节点）
redis-cli --cluster add-node new_node:7006