package com.example.demo.crud.service.impl;
import com.example.demo.crud.dto.RolePermissionsDTO;
import com.example.demo.crud.entity.RolePermissions;

import com.example.demo.crud.service.RolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.utils.PaginationUtil;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import com.example.demo.crud.vo.RolePermissionsVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.crud.mapper.RolePermissionsMapper;

@Service
@Transactional
public class RolePermissionsServiceImpl implements RolePermissionsService {
    @Autowired
    private RolePermissionsMapper rolePermissionsMapper;


   @Override
   public PaginationUtil<RolePermissionsDTO> page(Long pageNo, Long pageSize) {
        Page<RolePermissions> page = new Page(pageNo,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        IPage<RolePermissions> pagination = rolePermissionsMapper.selectPage(page, queryWrapper);
        List<RolePermissionsDTO> record = pagination.getRecords()
        .stream().map(RolePermissionsDTO::new)
        .collect(Collectors.toList());

        return PaginationUtil.of(record, pagination.getCurrent(),pagination.getSize(),pagination.getTotal());
    }

    @Override
    public List<RolePermissionsDTO> findAll() {
        return rolePermissionsMapper.selectList(null).stream()
                .map(RolePermissionsDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public RolePermissionsDTO findById(Long id) {
        return Optional.of(rolePermissionsMapper.selectById(id))
                .map(RolePermissionsDTO::new)
                .orElse(null);
    }

    @Override
    public Long save(RolePermissionsVO rolePermissionsVO) {
        RolePermissions entity = new RolePermissions();
        BeanUtils.copyProperties(rolePermissionsVO, entity);
        return Long.valueOf(rolePermissionsMapper.insert(entity));
    }

    @Override
    public Long deleteById(Long id) {
        return Long.valueOf(rolePermissionsMapper.deleteById(id));
    }

    @Override
    public Long updateById(Long id, RolePermissionsVO rolePermissionsVO) {
        RolePermissions entity = new RolePermissions();
        BeanUtils.copyProperties(rolePermissionsVO, entity);
        rolePermissionsMapper.updateById(entity);
        return id;
    }

    
};