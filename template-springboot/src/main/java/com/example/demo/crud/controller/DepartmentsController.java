package com.example.demo.crud.controller;
import com.example.demo.crud.dto.DepartmentsDTO;
import com.example.demo.crud.service.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.utils.ApiResponseUtil;
import com.example.demo.utils.PaginationUtil;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.crud.vo.DepartmentsVO;
import com.example.demo.foundation.annotation.Permission;
import com.example.demo.foundation.annotation.PermissionAction;
import com.example.demo.foundation.enums.PermissionsTypes;


@RestController
@RequestMapping("/api/v1/white-list/Departments")
@Permission(code = "departments", name = "Departments")
public class DepartmentsController {
    @Autowired
    private DepartmentsService departmentsService;

    @PermissionAction(PermissionsTypes.R)
    @GetMapping
    public ApiResponseUtil<List<DepartmentsDTO>> findAll() {
        return ApiResponseUtil.success(departmentsService.findAll());
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/page")
    public ApiResponseUtil<PaginationUtil<DepartmentsDTO>> page(@RequestParam(defaultValue = "0") Long pageNo,
                                                 @RequestParam(defaultValue = "10") Long pageSize) {
        return ApiResponseUtil.success(departmentsService.page(pageNo,pageSize));
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/{id}")
    public ApiResponseUtil<DepartmentsDTO> findById(@PathVariable Long id) {
        return ApiResponseUtil.success(departmentsService.findById(id));
    }

    @PermissionAction(PermissionsTypes.C)
    @PostMapping
    public ApiResponseUtil<Long> save(@RequestBody DepartmentsVO departmentsVO) {
        return ApiResponseUtil.success(departmentsService.save(departmentsVO));
    }

    @PermissionAction(PermissionsTypes.D)
    @DeleteMapping("/{id}")
    public ApiResponseUtil deleteById(@PathVariable Long id) {
        departmentsService.deleteById(id);
       return ApiResponseUtil.success(id);
    }

    @PermissionAction(PermissionsTypes.U)
    @PutMapping("/{id}")
    public ApiResponseUtil putById(@PathVariable Long id, DepartmentsVO departmentsVO) {
        departmentsService.updateById(id, departmentsVO);
        return ApiResponseUtil.success(id);
    }
}