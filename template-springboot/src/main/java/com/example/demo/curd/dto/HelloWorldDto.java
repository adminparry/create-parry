package com.example.demo.curd.dto;

import com.example.demo.curd.entity.HelloWorldEntity;
import com.example.demo.curd.entity.IndexEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HelloWorldDto {

    private String a;
    private String b;
    private String c;
    private String d;

    public HelloWorldDto(HelloWorldEntity helloWorldEntity) {
        this.a = helloWorldEntity.getV1();
        this.b = helloWorldEntity.getV2();
        this.c = helloWorldEntity.getV3();
    }

    public HelloWorldDto(IndexEntity helloWorldEntity) {
        this.a = helloWorldEntity.getV1();
        this.b = helloWorldEntity.getV2();
        this.c = helloWorldEntity.getV3();
    }
}
