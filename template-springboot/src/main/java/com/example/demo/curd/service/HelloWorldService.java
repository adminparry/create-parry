package com.example.demo.curd.service;

import com.example.demo.curd.dto.HelloWorldDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
public interface HelloWorldService {


    List<HelloWorldDto> selectByHelloWorld();

    List<HelloWorldDto> selectAll();
}
