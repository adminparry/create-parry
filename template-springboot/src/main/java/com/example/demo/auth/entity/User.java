package com.example.demo.auth.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "com.example.demo.utils.SnowflakeUtil")
    private Long id;

    @Column(unique=true)
    private String username;

    private String password;

    private String uf;

    private boolean deleted;
    private boolean enable;
    private String role;

    private LocalTime createTime;
    private LocalTime updateTime;
    private String createUser;
    private String department;


}
