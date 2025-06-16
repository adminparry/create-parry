package com.example.demo.crud.service;

import com.example.demo.utils.PaginationUtil;
import java.util.List;
import com.example.demo.crud.dto.PermissionsDTO;
import com.example.demo.crud.vo.PermissionsVO;


public interface PermissionsService {

    List<PermissionsDTO> findAll();

    PermissionsDTO  findById(Long id);

    Long save(PermissionsVO permissionsVO);

    Long deleteById(Long id);

    Long updateById(Long id, PermissionsVO permissionsVO);

    PaginationUtil<PermissionsDTO> page(Long page, Long size);
}
