package com.example.demo.crud.service.impl;
import com.example.demo.crud.dto.PermissionsDTO;
import com.example.demo.crud.entity.Permissions;

import com.example.demo.crud.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.utils.PaginationUtil;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import com.example.demo.crud.vo.PermissionsVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.crud.mapper.PermissionsMapper;

@Service
@Transactional
public class PermissionsServiceImpl implements PermissionsService {
    @Autowired
    private PermissionsMapper permissionsMapper;


   @Override
   public PaginationUtil<PermissionsDTO> page(Long pageNo, Long pageSize) {
        Page<Permissions> page = new Page(pageNo,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        IPage<Permissions> pagination = permissionsMapper.selectPage(page, queryWrapper);
        List<PermissionsDTO> record = pagination.getRecords()
        .stream().map(PermissionsDTO::new)
        .collect(Collectors.toList());

        return PaginationUtil.of(record, pagination.getCurrent(),pagination.getSize(),pagination.getTotal());
    }

    @Override
    public List<PermissionsDTO> findAll() {
        return permissionsMapper.selectList(null).stream()
                .map(PermissionsDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public PermissionsDTO findById(Long id) {
        return Optional.of(permissionsMapper.selectById(id))
                .map(PermissionsDTO::new)
                .orElse(null);
    }

    @Override
    public Long save(PermissionsVO permissionsVO) {
        Permissions entity = new Permissions();
        BeanUtils.copyProperties(permissionsVO, entity);
        return Long.valueOf(permissionsMapper.insert(entity));
    }

    @Override
    public Long deleteById(Long id) {
        return Long.valueOf(permissionsMapper.deleteById(id));
    }

    @Override
    public Long updateById(Long id, PermissionsVO permissionsVO) {
        Permissions entity = new Permissions();
        BeanUtils.copyProperties(permissionsVO, entity);
        permissionsMapper.updateById(entity);
        return id;
    }

    
};