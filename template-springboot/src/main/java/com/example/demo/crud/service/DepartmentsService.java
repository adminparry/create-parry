package com.example.demo.crud.service;

import com.example.demo.utils.PaginationUtil;
import java.util.List;
import com.example.demo.crud.dto.DepartmentsDTO;
import com.example.demo.crud.vo.DepartmentsVO;


public interface DepartmentsService {

    List<DepartmentsDTO> findAll();

    DepartmentsDTO  findById(Long id);

    Long save(DepartmentsVO departmentsVO);

    Long deleteById(Long id);

    Long updateById(Long id, DepartmentsVO departmentsVO);

    PaginationUtil<DepartmentsDTO> page(Long page, Long size);
}
