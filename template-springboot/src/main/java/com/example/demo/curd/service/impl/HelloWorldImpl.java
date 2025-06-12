package com.example.demo.curd.service.impl;

import com.example.demo.curd.repository.IndexRepository;
import com.example.demo.curd.dto.HelloWorldDto;
import com.example.demo.curd.entity.HelloWorldEntity;
import com.example.demo.curd.mapper.HelloWorldMapper;
import com.example.demo.curd.service.HelloWorldService;
import com.example.demo.curd.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HelloWorldImpl extends BaseService<HelloWorldMapper, HelloWorldEntity> implements HelloWorldService {

    @Override
    public List<HelloWorldDto> selectByHelloWorld() {
        return super.query().list().stream().map(HelloWorldDto::new).collect(Collectors.toList());

    }

    @Autowired
    private IndexRepository indexRepository;

    @Override
    public List<HelloWorldDto> selectAll() {

        return indexRepository.findAll().stream().map(HelloWorldDto::new).collect(Collectors.toList());
    }

}
