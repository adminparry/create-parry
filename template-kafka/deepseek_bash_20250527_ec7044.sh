# 创建ACL规则示例
kafka-acls.sh --authorizer-properties zookeeper.connect=localhost:2181 \
--add --allow-principal User:Alice --operation All --topic test-topic