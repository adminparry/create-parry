package com.example.demo.foundation.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.TimeUnit;

@Service
public class JwtTokenManager {

    @Value("spring.redis.tokenPrefix")
    private void setTokenKeyPrefix(String prefix){
        JwtTokenManager.TOKEN_KEY_PREFIX = prefix;
    }
    @Value("spring.redis.tokenExpire")
    private void setTokenExpire(int hours){
        JwtTokenManager.TOKEN_KEY_EXPIRE = hours;
    }
    private static String TOKEN_KEY_PREFIX;
    private static int TOKEN_KEY_EXPIRE;

    @Autowired
    private RedisTemplate<String,  String> redisTemplate;

    public void storeToken(String username, String token) {
        String key = TOKEN_KEY_PREFIX + username;
        redisTemplate.opsForValue().set(key, token, TOKEN_KEY_EXPIRE, TimeUnit.HOURS);
    }

    public String getToken(String username) {
        String key = TOKEN_KEY_PREFIX + username;
        return redisTemplate.opsForValue().get(key);
    }

    public void removeToken(String username) {
        String key = TOKEN_KEY_PREFIX + username;
        redisTemplate.delete(key);
    }

    public boolean validateToken(String username, String token) {
        String storedToken = getToken(username);
        return token != null && token.equals(storedToken);
    }

}
