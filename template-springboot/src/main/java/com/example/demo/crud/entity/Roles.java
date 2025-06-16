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
@TableName(value = "roles")
@Accessors(chain = true)
@Data
public class Roles extends BaseEntityUtil {

    @Schema(description = "")
    @TableId(type = IdType.ASSIGN_ID)
    private Integer roleId;

    @Schema(description = "")
    private String roleName;

    @Schema(description = "")
    private String description;

    @Schema(description = "1-启用, 0-禁用")
    private Integer status;

    @Schema(description = "")
    private LocalDateTime createdAt;

    @Schema(description = "")
    private LocalDateTime updatedAt;
}