package com.example.demo.crud.controller;
import com.example.demo.crud.dto.PermissionsDTO;
import com.example.demo.crud.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.utils.ApiResponseUtil;
import com.example.demo.utils.PaginationUtil;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.crud.vo.PermissionsVO;
import com.example.demo.foundation.annotation.Permission;
import com.example.demo.foundation.annotation.PermissionAction;
import com.example.demo.foundation.enums.PermissionsTypes;


@RestController
@RequestMapping("/api/v1/white-list/Permissions")
@Permission(code = "permissions", name = "Permissions")
public class PermissionsController {
    @Autowired
    private PermissionsService permissionsService;

    @PermissionAction(PermissionsTypes.R)
    @GetMapping
    public ApiResponseUtil<List<PermissionsDTO>> findAll() {
        return ApiResponseUtil.success(permissionsService.findAll());
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/page")
    public ApiResponseUtil<PaginationUtil<PermissionsDTO>> page(@RequestParam(defaultValue = "0") Long pageNo,
                                                 @RequestParam(defaultValue = "10") Long pageSize) {
        return ApiResponseUtil.success(permissionsService.page(pageNo,pageSize));
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/{id}")
    public ApiResponseUtil<PermissionsDTO> findById(@PathVariable Long id) {
        return ApiResponseUtil.success(permissionsService.findById(id));
    }

    @PermissionAction(PermissionsTypes.C)
    @PostMapping
    public ApiResponseUtil<Long> save(@RequestBody PermissionsVO permissionsVO) {
        return ApiResponseUtil.success(permissionsService.save(permissionsVO));
    }

    @PermissionAction(PermissionsTypes.D)
    @DeleteMapping("/{id}")
    public ApiResponseUtil deleteById(@PathVariable Long id) {
        permissionsService.deleteById(id);
       return ApiResponseUtil.success(id);
    }

    @PermissionAction(PermissionsTypes.U)
    @PutMapping("/{id}")
    public ApiResponseUtil putById(@PathVariable Long id, PermissionsVO permissionsVO) {
        permissionsService.updateById(id, permissionsVO);
        return ApiResponseUtil.success(id);
    }
}