package com.example.demo.crud.service.impl;
import com.example.demo.crud.dto.UsersDTO;
import com.example.demo.crud.entity.Users;

import com.example.demo.crud.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.utils.PaginationUtil;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import com.example.demo.crud.vo.UsersVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.crud.mapper.UsersMapper;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;


   @Override
   public PaginationUtil<UsersDTO> page(Long pageNo, Long pageSize) {
        Page<Users> page = new Page(pageNo,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        IPage<Users> pagination = usersMapper.selectPage(page, queryWrapper);
        List<UsersDTO> record = pagination.getRecords()
        .stream().map(this::convertToDTO)
        .collect(Collectors.toList());

        return PaginationUtil.of(record, pagination.getCurrent(),pagination.getSize(),pagination.getTotal());
    }

    @Override
    public List<UsersDTO> findAll() {
        return usersMapper.selectList(null).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsersDTO findById(Long id) {
        return Optional.of(usersMapper.selectById(id))
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public Long save(UsersVO usersVO) {
        Users entity = new Users();
        BeanUtils.copyProperties(usersVO, entity);
        return Long.valueOf(usersMapper.insert(entity));
    }

    @Override
    public Long deleteById(Long id) {
        return Long.valueOf(usersMapper.deleteById(id));
    }

    @Override
    public Long updateById(Long id, UsersVO usersVO) {
        Users entity = new Users();
        BeanUtils.copyProperties(usersVO, entity);
        usersMapper.updateById(entity);
        return id;
    }

    private UsersDTO convertToDTO(Users users) {
        // 实现转换逻辑
        return new UsersDTO();
    }
    private Users convertToEntity(UsersDTO usersDTO) {
        // 实现转换逻辑
        return new Users();
    }
};