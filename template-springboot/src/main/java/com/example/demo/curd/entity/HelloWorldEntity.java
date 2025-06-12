package com.example.demo.curd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Schema(description = "用户实体")  // 实体类上使用
@Data
@TableName(value = "hello_world")
public class HelloWorldEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "用户名", example = "admin")  // 实体字段上使用
    private String v1;
    private String v2;
    private String v3;
    private String v4;
    private String v5;
}
