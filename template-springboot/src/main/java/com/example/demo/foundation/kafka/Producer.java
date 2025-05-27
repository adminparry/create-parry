package com.example.demo.foundation.kafka;

import org.springframework.stereotype.Service;

import org.springframework.kafka.core.KafkaTemplate;

@Service
public class Producer {
    private final KafkaTemplate<String, String> kafkaTemplate;


    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message){
        kafkaTemplate.send(topic, message);
        System.out.println("send message: " + message);
    }
}
