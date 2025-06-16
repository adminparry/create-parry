package com.example.demo.crud.service;

import com.example.demo.utils.PaginationUtil;
import java.util.List;
import com.example.demo.crud.dto.UserDepartmentsDTO;
import com.example.demo.crud.vo.UserDepartmentsVO;


public interface UserDepartmentsService {

    List<UserDepartmentsDTO> findAll();

    UserDepartmentsDTO  findById(Long id);

    Long save(UserDepartmentsVO userDepartmentsVO);

    Long deleteById(Long id);

    Long updateById(Long id, UserDepartmentsVO userDepartmentsVO);

    PaginationUtil<UserDepartmentsDTO> page(Long page, Long size);
}
