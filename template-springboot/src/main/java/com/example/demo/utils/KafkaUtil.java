package com.example.demo.utils;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaUtil {

    /**
     * 创建Kafka生产者
     * @param bootstrapServers Kafka服务器地址，多个用逗号分隔
     * @return KafkaProducer实例
     */
    public static KafkaProducer<String, String> createProducer(String bootstrapServers) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 可选配置：提高可靠性
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        return new KafkaProducer<>(props);
    }

    /**
     * 创建Kafka消费者
     * @param bootstrapServers Kafka服务器地址，多个用逗号分隔
     * @param groupId 消费者组ID
     * @return KafkaConsumer实例
     */
    public static KafkaConsumer<String, String> createConsumer(String bootstrapServers, String groupId) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // 可选配置：从最早的消息开始消费
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 关闭自动提交偏移量，改为手动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return new KafkaConsumer<>(props);
    }

    /**
     * 发送消息
     * @param producer Kafka生产者
     * @param topic 主题
     * @param message 消息内容
     */
    public static void sendMessage(KafkaProducer<String, String> producer, String topic, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        try {
            // 同步发送消息
            producer.send(record).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送带键的消息
     * @param producer Kafka生产者
     * @param topic 主题
     * @param key 消息键
     * @param message 消息内容
     */
    public static void sendMessageWithKey(KafkaProducer<String, String> producer, String topic,
                                          String key, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            } else {
                System.out.printf("消息发送成功，主题：%s，分区：%d，偏移量：%d%n",
                        metadata.topic(), metadata.partition(), metadata.offset());
            }
        });
    }

    /**
     * 消费消息
     * @param consumer Kafka消费者
     * @param topic 主题
     * @param duration 每次poll的超时时间
     */
    public static void consumeMessages(KafkaConsumer<String, String> consumer,
                                       String topic, Duration duration) {
        consumer.subscribe(Collections.singletonList(topic));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(duration);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("收到消息：主题=%s，分区=%d，偏移量=%d，键=%s，值=%s%n",
                            record.topic(), record.partition(), record.offset(),
                            record.key(), record.value());
                }
                // 手动提交偏移量
                consumer.commitSync();
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 关闭Kafka生产者
     * @param producer Kafka生产者
     */
    public static void closeProducer(KafkaProducer<String, String> producer) {
        if (producer != null) {
            producer.close();
        }
    }

    /**
     * 关闭Kafka消费者
     * @param consumer Kafka消费者
     */
    public static void closeConsumer(KafkaConsumer<String, String> consumer) {
        if (consumer != null) {
            consumer.close();
        }
    }
}