package com.example.demo.curd.service;


import org.apache.ibatis.annotations.Mapper;

public interface SeckillService {

//    分布式锁

    boolean seckill(Long productId, Long userId);
//    活动时间校验逻辑
    boolean isSeckillActive(Long productId);
//    实现订单创建逻辑
    void createOrder(Long productId, Long userId);
}
