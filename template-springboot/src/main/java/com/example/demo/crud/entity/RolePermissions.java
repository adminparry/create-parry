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
@TableName(value = "role_permissions")
@Accessors(chain = true)
@Data
public class RolePermissions extends BaseEntityUtil {

    @Schema(description = "")
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    @Schema(description = "")
    private Integer roleId;

    @Schema(description = "")
    private Integer permissionId;

    @Schema(description = "")
    private LocalDateTime createdAt;
}