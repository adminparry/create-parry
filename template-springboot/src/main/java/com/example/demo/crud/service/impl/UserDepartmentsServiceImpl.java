package com.example.demo.crud.service.impl;
import com.example.demo.crud.dto.UserDepartmentsDTO;
import com.example.demo.crud.entity.UserDepartments;

import com.example.demo.crud.service.UserDepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.utils.PaginationUtil;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import com.example.demo.crud.vo.UserDepartmentsVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.crud.mapper.UserDepartmentsMapper;

@Service
@Transactional
public class UserDepartmentsServiceImpl implements UserDepartmentsService {
    @Autowired
    private UserDepartmentsMapper userDepartmentsMapper;


   @Override
   public PaginationUtil<UserDepartmentsDTO> page(Long pageNo, Long pageSize) {
        Page<UserDepartments> page = new Page(pageNo,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        IPage<UserDepartments> pagination = userDepartmentsMapper.selectPage(page, queryWrapper);
        List<UserDepartmentsDTO> record = pagination.getRecords()
        .stream().map(UserDepartmentsDTO::new)
        .collect(Collectors.toList());

        return PaginationUtil.of(record, pagination.getCurrent(),pagination.getSize(),pagination.getTotal());
    }

    @Override
    public List<UserDepartmentsDTO> findAll() {
        return userDepartmentsMapper.selectList(null).stream()
                .map(UserDepartmentsDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDepartmentsDTO findById(Long id) {
        return Optional.of(userDepartmentsMapper.selectById(id))
                .map(UserDepartmentsDTO::new)
                .orElse(null);
    }

    @Override
    public Long save(UserDepartmentsVO userDepartmentsVO) {
        UserDepartments entity = new UserDepartments();
        BeanUtils.copyProperties(userDepartmentsVO, entity);
        return Long.valueOf(userDepartmentsMapper.insert(entity));
    }

    @Override
    public Long deleteById(Long id) {
        return Long.valueOf(userDepartmentsMapper.deleteById(id));
    }

    @Override
    public Long updateById(Long id, UserDepartmentsVO userDepartmentsVO) {
        UserDepartments entity = new UserDepartments();
        BeanUtils.copyProperties(userDepartmentsVO, entity);
        userDepartmentsMapper.updateById(entity);
        return id;
    }

    
};