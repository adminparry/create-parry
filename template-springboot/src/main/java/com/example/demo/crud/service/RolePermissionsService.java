package com.example.demo.crud.service;

import com.example.demo.utils.PaginationUtil;
import java.util.List;
import com.example.demo.crud.dto.RolePermissionsDTO;
import com.example.demo.crud.vo.RolePermissionsVO;


public interface RolePermissionsService {

    List<RolePermissionsDTO> findAll();

    RolePermissionsDTO  findById(Long id);

    Long save(RolePermissionsVO rolePermissionsVO);

    Long deleteById(Long id);

    Long updateById(Long id, RolePermissionsVO rolePermissionsVO);

    PaginationUtil<RolePermissionsDTO> page(Long page, Long size);
}
