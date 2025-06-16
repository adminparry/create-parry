package com.example.demo.crud.service.impl;
import com.example.demo.crud.dto.UserRolesDTO;
import com.example.demo.crud.entity.UserRoles;

import com.example.demo.crud.service.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.utils.PaginationUtil;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import com.example.demo.crud.vo.UserRolesVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.crud.mapper.UserRolesMapper;

@Service
@Transactional
public class UserRolesServiceImpl implements UserRolesService {
    @Autowired
    private UserRolesMapper userRolesMapper;


   @Override
   public PaginationUtil<UserRolesDTO> page(Long pageNo, Long pageSize) {
        Page<UserRoles> page = new Page(pageNo,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        IPage<UserRoles> pagination = userRolesMapper.selectPage(page, queryWrapper);
        List<UserRolesDTO> record = pagination.getRecords()
        .stream().map(UserRolesDTO::new)
        .collect(Collectors.toList());

        return PaginationUtil.of(record, pagination.getCurrent(),pagination.getSize(),pagination.getTotal());
    }

    @Override
    public List<UserRolesDTO> findAll() {
        return userRolesMapper.selectList(null).stream()
                .map(UserRolesDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserRolesDTO findById(Long id) {
        return Optional.of(userRolesMapper.selectById(id))
                .map(UserRolesDTO::new)
                .orElse(null);
    }

    @Override
    public Long save(UserRolesVO userRolesVO) {
        UserRoles entity = new UserRoles();
        BeanUtils.copyProperties(userRolesVO, entity);
        return Long.valueOf(userRolesMapper.insert(entity));
    }

    @Override
    public Long deleteById(Long id) {
        return Long.valueOf(userRolesMapper.deleteById(id));
    }

    @Override
    public Long updateById(Long id, UserRolesVO userRolesVO) {
        UserRoles entity = new UserRoles();
        BeanUtils.copyProperties(userRolesVO, entity);
        userRolesMapper.updateById(entity);
        return id;
    }

    
};