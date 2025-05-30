package com.example.demo.curd.controller;

import com.example.demo.curd.service.SeckillService;
import com.example.demo.foundation.annotation.Index;
import com.example.demo.curd.dto.HelloWorldDto;
import com.example.demo.curd.service.HelloWorldService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloWorld {


    @Autowired
    private HelloWorldService helloWorldService;


    @Autowired
    private SeckillService seckillService;



    @RequestMapping("/mybatis")
    public List<HelloWorldDto> world(){
        return helloWorldService.selectByHelloWorld();
    }

    @Tag(name = "用户管理")  // 类上使用
    @Operation(summary = "创建用户", description = "根据User对象创建用户")  // 方法上使用
    @Parameter(name = "id", description = "用户ID", required = true)  // 参数上使用
    @Hidden  // 忽略某个方法或参数
    @RequestMapping("/jpa")
    public List<HelloWorldDto> hibernate(){
        return helloWorldService.selectAll();
    }




}
