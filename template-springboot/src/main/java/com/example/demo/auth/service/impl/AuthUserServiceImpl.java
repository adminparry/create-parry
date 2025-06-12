package com.example.demo.auth.service.impl;

import com.example.demo.auth.entity.User;
import com.example.demo.auth.service.AuthUserService;
import com.example.demo.foundation.constants.Sso;
import com.example.demo.foundation.exception.BusinessException;
import com.example.demo.foundation.filter.SQLFilter;
import com.example.demo.utils.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Service
@Slf4j
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private  EntityManager entityManager;
    @Override
    public User authenticate(String user, String pass) throws Exception {
        TypedQuery<User> query = entityManager.createQuery(
                            "select u from User u where u.username= :username and u.password = :password", User.class)
                    .setParameter("username", SQLFilter.sqlInject(user))
                    .setParameter("password", SQLFilter.sqlInject(pass));


        return JpaUtils.getSingleResultOrEmpty(query).orElseThrow(() -> new BusinessException(Sso.VALIDATE1));
    }
}
