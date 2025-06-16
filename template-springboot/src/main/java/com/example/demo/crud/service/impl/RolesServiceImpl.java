package com.example.demo.crud.service.impl;
import com.example.demo.crud.dto.RolesDTO;
import com.example.demo.crud.entity.Roles;

import com.example.demo.crud.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.utils.PaginationUtil;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import com.example.demo.crud.vo.RolesVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.crud.mapper.RolesMapper;

@Service
@Transactional
public class RolesServiceImpl implements RolesService {
    @Autowired
    private RolesMapper rolesMapper;


   @Override
   public PaginationUtil<RolesDTO> page(Long pageNo, Long pageSize) {
        Page<Roles> page = new Page(pageNo,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        IPage<Roles> pagination = rolesMapper.selectPage(page, queryWrapper);
        List<RolesDTO> record = pagination.getRecords()
        .stream().map(RolesDTO::new)
        .collect(Collectors.toList());

        return PaginationUtil.of(record, pagination.getCurrent(),pagination.getSize(),pagination.getTotal());
    }

    @Override
    public List<RolesDTO> findAll() {
        return rolesMapper.selectList(null).stream()
                .map(RolesDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public RolesDTO findById(Long id) {
        return Optional.of(rolesMapper.selectById(id))
                .map(RolesDTO::new)
                .orElse(null);
    }

    @Override
    public Long save(RolesVO rolesVO) {
        Roles entity = new Roles();
        BeanUtils.copyProperties(rolesVO, entity);
        return Long.valueOf(rolesMapper.insert(entity));
    }

    @Override
    public Long deleteById(Long id) {
        return Long.valueOf(rolesMapper.deleteById(id));
    }

    @Override
    public Long updateById(Long id, RolesVO rolesVO) {
        Roles entity = new Roles();
        BeanUtils.copyProperties(rolesVO, entity);
        rolesMapper.updateById(entity);
        return id;
    }

    
};