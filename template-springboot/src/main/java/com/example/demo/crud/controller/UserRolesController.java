package com.example.demo.crud.controller;
import com.example.demo.crud.dto.UserRolesDTO;
import com.example.demo.crud.service.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.utils.ApiResponseUtil;
import com.example.demo.utils.PaginationUtil;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.crud.vo.UserRolesVO;
import com.example.demo.foundation.annotation.Permission;
import com.example.demo.foundation.annotation.PermissionAction;
import com.example.demo.foundation.enums.PermissionsTypes;


@RestController
@RequestMapping("/api/v1/white-list/UserRoles")
@Permission(code = "userRoles", name = "UserRoles")
public class UserRolesController {
    @Autowired
    private UserRolesService userRolesService;

    @PermissionAction(PermissionsTypes.R)
    @GetMapping
    public ApiResponseUtil<List<UserRolesDTO>> findAll() {
        return ApiResponseUtil.success(userRolesService.findAll());
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/page")
    public ApiResponseUtil<PaginationUtil<UserRolesDTO>> page(@RequestParam(defaultValue = "0") Long pageNo,
                                                 @RequestParam(defaultValue = "10") Long pageSize) {
        return ApiResponseUtil.success(userRolesService.page(pageNo,pageSize));
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/{id}")
    public ApiResponseUtil<UserRolesDTO> findById(@PathVariable Long id) {
        return ApiResponseUtil.success(userRolesService.findById(id));
    }

    @PermissionAction(PermissionsTypes.C)
    @PostMapping
    public ApiResponseUtil<Long> save(@RequestBody UserRolesVO userRolesVO) {
        return ApiResponseUtil.success(userRolesService.save(userRolesVO));
    }

    @PermissionAction(PermissionsTypes.D)
    @DeleteMapping("/{id}")
    public ApiResponseUtil deleteById(@PathVariable Long id) {
        userRolesService.deleteById(id);
       return ApiResponseUtil.success(id);
    }

    @PermissionAction(PermissionsTypes.U)
    @PutMapping("/{id}")
    public ApiResponseUtil putById(@PathVariable Long id, UserRolesVO userRolesVO) {
        userRolesService.updateById(id, userRolesVO);
        return ApiResponseUtil.success(id);
    }
}