# 创建topic
kafka-topics.sh --create --bootstrap-server node1:9092 --replication-factor 3 --partitions 3 --topic test-topic

# 查看topic列表
kafka-topics.sh --list --bootstrap-server node1:9092

# 生产消息
kafka-console-producer.sh --broker-list node1:9092 --topic test-topic

# 消费消息
kafka-console-consumer.sh --bootstrap-server node1:9092 --topic test-topic --from-beginning

# 查看topic详情
kafka-topics.sh --describe --bootstrap-server node1:9092 --topic test-topic

# 停止Kafka
kafka-server-stop.sh

# 停止Zookeeper
zookeeper-server-stop.sh