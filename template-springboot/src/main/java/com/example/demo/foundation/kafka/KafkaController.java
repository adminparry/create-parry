package com.example.demo.foundation.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @RequestMapping("/publish")
    public String publish(@RequestParam("topic") String topic, @RequestParam("message") String message) {
        kafkaProducerService.sendMessage(topic, message);
        return "Message sent!";
        
    }
}
