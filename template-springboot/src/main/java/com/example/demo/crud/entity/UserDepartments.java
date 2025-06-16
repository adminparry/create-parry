package com.example.demo.crud.entity;

import lombok.Data;
import java.time.LocalDateTime;
import com.example.demo.utils.BaseEntityUtil;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Schema(description  =  "")
@TableName(value = "user_departments")
@Accessors(chain = true)
@Data
public class UserDepartments extends BaseEntityUtil {

    @Schema(description = "")
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    @Schema(description = "")
    private Integer userId;

    @Schema(description = "")
    private Integer deptId;

    @Schema(description = "是否部门负责人")
    private Integer isLeader;

    @Schema(description = "")
    private LocalDateTime createdAt;
}