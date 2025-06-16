package com.example.demo.crud.service.impl;
import com.example.demo.crud.dto.DepartmentsDTO;
import com.example.demo.crud.entity.Departments;

import com.example.demo.crud.service.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.utils.PaginationUtil;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import com.example.demo.crud.vo.DepartmentsVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.crud.mapper.DepartmentsMapper;

@Service
@Transactional
public class DepartmentsServiceImpl implements DepartmentsService {
    @Autowired
    private DepartmentsMapper departmentsMapper;


   @Override
   public PaginationUtil<DepartmentsDTO> page(Long pageNo, Long pageSize) {
        Page<Departments> page = new Page(pageNo,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        IPage<Departments> pagination = departmentsMapper.selectPage(page, queryWrapper);
        List<DepartmentsDTO> record = pagination.getRecords()
        .stream().map(DepartmentsDTO::new)
        .collect(Collectors.toList());

        return PaginationUtil.of(record, pagination.getCurrent(),pagination.getSize(),pagination.getTotal());
    }

    @Override
    public List<DepartmentsDTO> findAll() {
        return departmentsMapper.selectList(null).stream()
                .map(DepartmentsDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentsDTO findById(Long id) {
        return Optional.of(departmentsMapper.selectById(id))
                .map(DepartmentsDTO::new)
                .orElse(null);
    }

    @Override
    public Long save(DepartmentsVO departmentsVO) {
        Departments entity = new Departments();
        BeanUtils.copyProperties(departmentsVO, entity);
        return Long.valueOf(departmentsMapper.insert(entity));
    }

    @Override
    public Long deleteById(Long id) {
        return Long.valueOf(departmentsMapper.deleteById(id));
    }

    @Override
    public Long updateById(Long id, DepartmentsVO departmentsVO) {
        Departments entity = new Departments();
        BeanUtils.copyProperties(departmentsVO, entity);
        departmentsMapper.updateById(entity);
        return id;
    }

    
};