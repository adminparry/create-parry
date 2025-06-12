package com.example.demo.curd.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createUser;
    private String updateUser;
    private boolean deleted;

}
