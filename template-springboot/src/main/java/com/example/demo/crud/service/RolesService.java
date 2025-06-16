package com.example.demo.crud.service;

import com.example.demo.utils.PaginationUtil;
import java.util.List;
import com.example.demo.crud.dto.RolesDTO;
import com.example.demo.crud.vo.RolesVO;


public interface RolesService {

    List<RolesDTO> findAll();

    RolesDTO  findById(Long id);

    Long save(RolesVO rolesVO);

    Long deleteById(Long id);

    Long updateById(Long id, RolesVO rolesVO);

    PaginationUtil<RolesDTO> page(Long page, Long size);
}
