package com.example.demo.foundation.kafka;

import org.springframework.stereotype.Service;

import org.springframework.kafka.core.KafkaTemplate;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;


    // 构造函数，传入KafkaTemplate<String, String>类型的参数
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        // 将传入的参数赋值给成员变量
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message){
        kafkaTemplate.send(topic, message).addCallback(
            result -> {
                if(null != result) {
                    System.out.println("send message failed: " + message + " with offset" + result.getRecordMetadata().offset() + "");
                }
            },
            ex -> {
                System.out.println("send message failed: " + message  + "coused by: " + ex.getMessage());
            }
        );;
        System.out.println("send message: " + message);
    }

    public void sendMessageWithKey(String topic, String key, String message) {
        kafkaTemplate.send(topic, key, message);
    }
}
