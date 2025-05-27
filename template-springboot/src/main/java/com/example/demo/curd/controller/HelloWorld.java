package com.example.demo.curd.controller;

import com.example.demo.curd.service.SeckillService;
import com.example.demo.foundation.annotation.Index;
import com.example.demo.curd.dto.HelloWorldDto;
import com.example.demo.curd.service.HelloWorldService;
import com.example.demo.foundation.kafka.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloWorld {


    @Autowired
    private HelloWorldService helloWorldService;

    @Autowired
    private Producer kafkaProducer;

    @Autowired
    private SeckillService seckillService;


    @Index("index")
    @RequestMapping
    public String hello() {
        kafkaProducer.sendMessage("a", "b");
        return "hello world for spring boot";
    }

    @RequestMapping("/mybatis")
    public List<HelloWorldDto> world(){
        return helloWorldService.selectByHelloWorld();
    }
    @RequestMapping("/jpa")
    public List<HelloWorldDto> hibernate(){
        return helloWorldService.selectAll();
    }




}
