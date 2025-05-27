package com.example.demo.curd.mapper;

import com.example.demo.curd.entity.HelloWorldEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HelloWorldMapper extends IndexMapper<HelloWorldEntity> {


}
