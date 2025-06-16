package com.example.demo.crud.service;

import com.example.demo.utils.PaginationUtil;
import java.util.List;
import com.example.demo.crud.dto.UsersDTO;
import com.example.demo.crud.vo.UsersVO;


public interface UsersService {

    List<UsersDTO> findAll();

    UsersDTO  findById(Long id);

    Long save(UsersVO usersVO);

    Long deleteById(Long id);

    Long updateById(Long id, UsersVO usersVO);

    PaginationUtil<UsersDTO> page(Long page, Long size);
}
