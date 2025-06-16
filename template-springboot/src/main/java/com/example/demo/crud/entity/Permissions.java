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
@TableName(value = "permissions")
@Accessors(chain = true)
@Data
public class Permissions extends BaseEntityUtil {

    @Schema(description = "")
    @TableId(type = IdType.ASSIGN_ID)
    private Integer permissionId;

    @Schema(description = "")
    private String permissionName;

    @Schema(description = "权限标识符，如user:create")
    private String permissionCode;

    @Schema(description = "")
    private String description;

    @Schema(description = "资源类型，如menu,button,api等")
    private String resourceType;

    @Schema(description = "资源路径")
    private String resourcePath;

    @Schema(description = "1-启用, 0-禁用")
    private Integer status;

    @Schema(description = "")
    private LocalDateTime createdAt;

    @Schema(description = "")
    private LocalDateTime updatedAt;
}