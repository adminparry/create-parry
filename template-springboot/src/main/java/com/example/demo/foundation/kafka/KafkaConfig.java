package com.example.demo.foundation.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;

@Configuration
public class KafkaConfig {

    // 定义Kafka的Bootstrap服务器地址
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    

    // 创建一个ProducerFactory对象，用于创建Kafka生产者
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        // 设置Kafka的Bootstrap服务器地址
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        // 设置Kafka生产者发送消息的key的序列化器
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // 设置Kafka生产者发送消息的value的序列化器
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializable.class);

        // 返回一个DefaultKafkaProducerFactory对象
        return new DefaultKafkaProducerFactory<>(config);

    }

    // 创建一个KafkaTemplate对象，用于发送消息到Kafka
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        // 使用producerFactory()方法创建一个KafkaTemplate对象
        return new KafkaTemplate<>(producerFactory());
    }

    private ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);

    }
    // 创建一个ConcurrentKafkaListenerContainerFactory对象，用于监听Kafka消息
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> batchFactory(ConsumerFactory<String, String> consumerFactory) {
            ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
            // 设置消费者工厂
            factory.setConsumerFactory(consumerFactory);
        // 设置批量监听
        factory.setBatchListener(true);
        return factory;
    }
    
}
