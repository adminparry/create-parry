package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional
public class SeckillServiceUtil {


    private static final String SECKILL_PREFIX = "seckill:";
    private static final String LOCK_PREFIX = "lock:";
    private static final long LOCK_EXPIRE = 30;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    public boolean seckill(Long productId, Long userId) {
        if(!isSeckillActive(productId))throw new RuntimeException("秒杀活动未开始或已结束");

        String lockKey = LOCK_PREFIX + productId + ":" + userId;

        Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "1", Duration.ofSeconds(LOCK_EXPIRE));

        if(locked == null || !locked){
            throw new RuntimeException("操作太频繁请稍后再试");
        }

        try {

            String stockKey = SECKILL_PREFIX + "stock:" + productId;

            Long stock = stringRedisTemplate.opsForValue().decrement(stockKey);

            if(stock == null || stock < 0){
                stringRedisTemplate.opsForValue().increment(stockKey);

                throw new RuntimeException("商品已售罄");
            }

            createOrder(productId, userId);

            return true;
        } finally {
            stringRedisTemplate.delete(lockKey);
        }
    }


    public boolean isSeckillActive(Long productId) {
        return true;
    }


    public void createOrder(Long productId, Long userId) {

    }
}
