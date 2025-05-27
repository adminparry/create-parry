package com.example.demo.curd.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "hello_world")
@Data
public class IndexEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String v1;
    private String v2;
    private String v3;
    private String v4;
    private String v5;
}
