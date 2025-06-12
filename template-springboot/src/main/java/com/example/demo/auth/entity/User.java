package com.example.demo.auth.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
