package com.example.demo.curd.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.example.demo.curd.mapper.IndexMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService<M extends IndexMapper<T>, T> {

    @Autowired
    protected M mapper;

    public BaseService() {
    }

    protected LambdaQueryChainWrapper<T> query() {
        return new LambdaQueryChainWrapper<>(this.mapper);
    }
}
