package com.example.demo.auth.service;

import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Collections;

public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private EntityManager entityManager;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            try{
                User user = entityManager.createQuery(
                        "select u from User u where u.username= :username", User.class).setParameter("username", username).getSingleResult();

                return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.isEnable(),
                        true,
                        true,
                        true,
                        Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
                );
            } catch (NoResultException e) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
    }
}
