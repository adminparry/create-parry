package com.example.demo.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class RedisUtilTest {


    @Autowired(required = false)
    private RedisUtil redisUtil;

    @Test
    public void testRedisConnection() {
        try {

            redisUtil.set("testKey", "testValue");
            String value = (String) redisUtil.get("testKey");

            assertEquals("testValue", value);
            System.out.println("Redis 连接测试成功");
        } catch (Exception e) {
            fail("Redis 连接失败: " + e.getMessage());
        } finally {
            redisUtil.delete("testKey");
        }
    }
}
