package com.example.demo.crud.controller;
import com.example.demo.crud.dto.UsersDTO;
import com.example.demo.crud.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.utils.ApiResponseUtil;
import com.example.demo.utils.PaginationUtil;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.crud.vo.UsersVO;


@RestController
@RequestMapping("/api/v1/white-list/Users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping
    public ApiResponseUtil<List<UsersDTO>> findAll() {
        return ApiResponseUtil.success(usersService.findAll());
    }

    @GetMapping("/page")
    public ApiResponseUtil<PaginationUtil<UsersDTO>> page(@RequestParam(defaultValue = "0") Long pageNo,
                                                 @RequestParam(defaultValue = "10") Long pageSize) {
        return ApiResponseUtil.success(usersService.page(pageNo,pageSize));
    }

    @GetMapping("/{id}")
    public ApiResponseUtil<UsersDTO> findById(@PathVariable Long id) {
        return ApiResponseUtil.success(usersService.findById(id));
    }

    @PostMapping
    public ApiResponseUtil<Long> save(@RequestBody UsersVO usersVO) {
        return ApiResponseUtil.success(usersService.save(usersVO));
    }

    @DeleteMapping("/{id}")
    public ApiResponseUtil deleteById(@PathVariable Long id) {
        usersService.deleteById(id);
       return ApiResponseUtil.success(id);
    }
}