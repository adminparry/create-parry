package com.example.demo.auth.service.impl;

import com.example.demo.auth.entity.User;
import com.example.demo.auth.exception.SsoException;
import com.example.demo.auth.repository.UserRepository;
import com.example.demo.auth.service.AuthUserService;
import com.example.demo.foundation.constants.Sso;
import com.example.demo.foundation.exception.BusinessException;
import com.example.demo.foundation.filter.SQLFilter;
import com.example.demo.utils.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private  EntityManager entityManager;
    @Resource
    private UserRepository userRepository;

    @Override
    public User authenticate(String user, String pass) throws Exception {

        User u = userRepository.findByUsername(user);
        if(null == u) {
            throw new SsoException("用户名不存在");
        }

        return u;
    }

    @Override
    public Long reg(String user, String pass, String salt) throws Exception {

        User userEntity = new User();
        userEntity.setUsername(user);
        userEntity.setPassword(pass);
        userEntity.setUf(salt);

        User u = userRepository.save(userEntity);

        return u.getId();
    }

}
