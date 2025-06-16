package com.example.demo.crud.controller;
import com.example.demo.crud.dto.UserDepartmentsDTO;
import com.example.demo.crud.service.UserDepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.utils.ApiResponseUtil;
import com.example.demo.utils.PaginationUtil;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.crud.vo.UserDepartmentsVO;
import com.example.demo.foundation.annotation.Permission;
import com.example.demo.foundation.annotation.PermissionAction;
import com.example.demo.foundation.enums.PermissionsTypes;


@RestController
@RequestMapping("/api/v1/white-list/UserDepartments")
@Permission(code = "userDepartments", name = "UserDepartments")
public class UserDepartmentsController {
    @Autowired
    private UserDepartmentsService userDepartmentsService;

    @PermissionAction(PermissionsTypes.R)
    @GetMapping
    public ApiResponseUtil<List<UserDepartmentsDTO>> findAll() {
        return ApiResponseUtil.success(userDepartmentsService.findAll());
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/page")
    public ApiResponseUtil<PaginationUtil<UserDepartmentsDTO>> page(@RequestParam(defaultValue = "0") Long pageNo,
                                                 @RequestParam(defaultValue = "10") Long pageSize) {
        return ApiResponseUtil.success(userDepartmentsService.page(pageNo,pageSize));
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/{id}")
    public ApiResponseUtil<UserDepartmentsDTO> findById(@PathVariable Long id) {
        return ApiResponseUtil.success(userDepartmentsService.findById(id));
    }

    @PermissionAction(PermissionsTypes.C)
    @PostMapping
    public ApiResponseUtil<Long> save(@RequestBody UserDepartmentsVO userDepartmentsVO) {
        return ApiResponseUtil.success(userDepartmentsService.save(userDepartmentsVO));
    }

    @PermissionAction(PermissionsTypes.D)
    @DeleteMapping("/{id}")
    public ApiResponseUtil deleteById(@PathVariable Long id) {
        userDepartmentsService.deleteById(id);
       return ApiResponseUtil.success(id);
    }

    @PermissionAction(PermissionsTypes.U)
    @PutMapping("/{id}")
    public ApiResponseUtil putById(@PathVariable Long id, UserDepartmentsVO userDepartmentsVO) {
        userDepartmentsService.updateById(id, userDepartmentsVO);
        return ApiResponseUtil.success(id);
    }
}