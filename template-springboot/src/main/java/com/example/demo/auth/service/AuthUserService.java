package com.example.demo.auth.service;

import com.example.demo.auth.entity.User;

public interface AuthUserService {

    User authenticate(String  user, String pass) throws Exception;
}
