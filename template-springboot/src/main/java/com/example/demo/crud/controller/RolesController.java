package com.example.demo.crud.controller;
import com.example.demo.crud.dto.RolesDTO;
import com.example.demo.crud.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.utils.ApiResponseUtil;
import com.example.demo.utils.PaginationUtil;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.crud.vo.RolesVO;
import com.example.demo.foundation.annotation.Permission;
import com.example.demo.foundation.annotation.PermissionAction;
import com.example.demo.foundation.enums.PermissionsTypes;


@RestController
@RequestMapping("/api/v1/white-list/Roles")
@Permission(code = "roles", name = "Roles")
public class RolesController {
    @Autowired
    private RolesService rolesService;

    @PermissionAction(PermissionsTypes.R)
    @GetMapping
    public ApiResponseUtil<List<RolesDTO>> findAll() {
        return ApiResponseUtil.success(rolesService.findAll());
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/page")
    public ApiResponseUtil<PaginationUtil<RolesDTO>> page(@RequestParam(defaultValue = "0") Long pageNo,
                                                 @RequestParam(defaultValue = "10") Long pageSize) {
        return ApiResponseUtil.success(rolesService.page(pageNo,pageSize));
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/{id}")
    public ApiResponseUtil<RolesDTO> findById(@PathVariable Long id) {
        return ApiResponseUtil.success(rolesService.findById(id));
    }

    @PermissionAction(PermissionsTypes.C)
    @PostMapping
    public ApiResponseUtil<Long> save(@RequestBody RolesVO rolesVO) {
        return ApiResponseUtil.success(rolesService.save(rolesVO));
    }

    @PermissionAction(PermissionsTypes.D)
    @DeleteMapping("/{id}")
    public ApiResponseUtil deleteById(@PathVariable Long id) {
        rolesService.deleteById(id);
       return ApiResponseUtil.success(id);
    }

    @PermissionAction(PermissionsTypes.U)
    @PutMapping("/{id}")
    public ApiResponseUtil putById(@PathVariable Long id, RolesVO rolesVO) {
        rolesService.updateById(id, rolesVO);
        return ApiResponseUtil.success(id);
    }
}