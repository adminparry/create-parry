package com.example.demo.curd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hello_world")
public class HelloWorldEntity {
    private String v1;
    private String v2;
    private String v3;
    private String v4;
    private String v5;
}
