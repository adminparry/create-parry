package com.example.demo.crud.mapper;

import com.example.demo.crud.entity.Users;
import com.example.demo.utils.BaseMapperUtil;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper extends BaseMapperUtil<Users> {
}
