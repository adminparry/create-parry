package com.example.demo.crud.controller;
import com.example.demo.crud.dto.RolePermissionsDTO;
import com.example.demo.crud.service.RolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.utils.ApiResponseUtil;
import com.example.demo.utils.PaginationUtil;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.crud.vo.RolePermissionsVO;
import com.example.demo.foundation.annotation.Permission;
import com.example.demo.foundation.annotation.PermissionAction;
import com.example.demo.foundation.enums.PermissionsTypes;


@RestController
@RequestMapping("/api/v1/white-list/RolePermissions")
@Permission(code = "rolePermissions", name = "RolePermissions")
public class RolePermissionsController {
    @Autowired
    private RolePermissionsService rolePermissionsService;

    @PermissionAction(PermissionsTypes.R)
    @GetMapping
    public ApiResponseUtil<List<RolePermissionsDTO>> findAll() {
        return ApiResponseUtil.success(rolePermissionsService.findAll());
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/page")
    public ApiResponseUtil<PaginationUtil<RolePermissionsDTO>> page(@RequestParam(defaultValue = "0") Long pageNo,
                                                 @RequestParam(defaultValue = "10") Long pageSize) {
        return ApiResponseUtil.success(rolePermissionsService.page(pageNo,pageSize));
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/{id}")
    public ApiResponseUtil<RolePermissionsDTO> findById(@PathVariable Long id) {
        return ApiResponseUtil.success(rolePermissionsService.findById(id));
    }

    @PermissionAction(PermissionsTypes.C)
    @PostMapping
    public ApiResponseUtil<Long> save(@RequestBody RolePermissionsVO rolePermissionsVO) {
        return ApiResponseUtil.success(rolePermissionsService.save(rolePermissionsVO));
    }

    @PermissionAction(PermissionsTypes.D)
    @DeleteMapping("/{id}")
    public ApiResponseUtil deleteById(@PathVariable Long id) {
        rolePermissionsService.deleteById(id);
       return ApiResponseUtil.success(id);
    }

    @PermissionAction(PermissionsTypes.U)
    @PutMapping("/{id}")
    public ApiResponseUtil putById(@PathVariable Long id, RolePermissionsVO rolePermissionsVO) {
        rolePermissionsService.updateById(id, rolePermissionsVO);
        return ApiResponseUtil.success(id);
    }
}