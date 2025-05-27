package com.example.demo.foundation.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    // 监听名为"my-topic"的主题，组名为"my-group"
    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void listen(String message) {
        // 打印接收到的消息
        System.out.println("Receive message: " + message);
    }


    // 监听名为"my-topic"和"my-topic2"的两个主题
    @KafkaListener(topics = {"my-topic, my-topic2"})
    public void listen2(String message) {
        System.out.println("Receive message: " + message);
    }

    // 监听名为headers-topic的主题
    @KafkaListener(topics = "headers-topic")
    public void listenWithHeaders(String msg, 
    // 获取接收到的消息的主题
    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
    // 获取接收到的消息的分区
    @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
    // 获取接收到的消息的偏移量
    @Header(KafkaHeaders.OFFSET) int offset
    
    ) {
        // 打印接收到的消息、主题、分区和偏移量
        System.out.println("Received message: " + msg + " from topic: " + topic + ", partition: " + partition + ", offset: " + offset);
    }

}
