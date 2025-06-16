package com.example.demo.crud.service;

import com.example.demo.utils.PaginationUtil;
import java.util.List;
import com.example.demo.crud.dto.UserRolesDTO;
import com.example.demo.crud.vo.UserRolesVO;


public interface UserRolesService {

    List<UserRolesDTO> findAll();

    UserRolesDTO  findById(Long id);

    Long save(UserRolesVO userRolesVO);

    Long deleteById(Long id);

    Long updateById(Long id, UserRolesVO userRolesVO);

    PaginationUtil<UserRolesDTO> page(Long page, Long size);
}
