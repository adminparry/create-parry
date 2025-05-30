package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQUtil {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQUtil(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送消息到默认交换机
     */
    public void sendMessage(String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(routingKey, message);
            log.info("Sent message to queue {} successfully", routingKey);
        } catch (Exception e) {
            log.error("Error sending message to queue {}: {}", routingKey, e.getMessage());
            throw e;
        }
    }

    /**
     * 发送消息到指定交换机
     */
    public void sendMessage(String exchange, String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            log.info("Sent message to exchange {} with routing key {} successfully", exchange, routingKey);
        } catch (Exception e) {
            log.error("Error sending message to exchange {} with routing key {}: {}", 
                     exchange, routingKey, e.getMessage());
            throw e;
        }
    }

    /**
     * 发送消息并设置消息属性
     */
    public void sendMessageWithProperties(String exchange, String routingKey, 
                                        Object message, MessageProperties properties) {
        try {
            MessageConverter converter = rabbitTemplate.getMessageConverter();
            Message msg = converter.toMessage(message, properties);
            rabbitTemplate.send(exchange, routingKey, msg);
            log.info("Sent message with properties to exchange {} with routing key {} successfully", 
                    exchange, routingKey);
        } catch (Exception e) {
            log.error("Error sending message with properties to exchange {} with routing key {}: {}", 
                     exchange, routingKey, e.getMessage());
            throw e;
        }
    }

    /**
     * 接收消息
     */
    public Object receiveMessage(String queueName) {
        try {
            return rabbitTemplate.receiveAndConvert(queueName);
        } catch (Exception e) {
            log.error("Error receiving message from queue {}: {}", queueName, e.getMessage());
            throw e;
        }
    }

    /**
     * 接收消息并等待响应
     */
    public Object sendAndReceive(String exchange, String routingKey, Object message) {
        try {
            return rabbitTemplate.convertSendAndReceive(exchange, routingKey, message);
        } catch (Exception e) {
            log.error("Error in send and receive from exchange {} with routing key {}: {}", 
                     exchange, routingKey, e.getMessage());
            throw e;
        }
    }
} 