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
@TableName(value = "users")
@Accessors(chain = true)
@Data
public class Users extends BaseEntityUtil {

    @Schema(description = "")
    @TableId(type = IdType.ASSIGN_ID)
    private Integer userId;

    @Schema(description = "")
    private String username;

    @Schema(description = "")
    private String password;

    @Schema(description = "")
    private String email;

    @Schema(description = "")
    private String realName;

    @Schema(description = "1-启用, 0-禁用")
    private Integer status;

    @Schema(description = "")
    private LocalDateTime createdAt;

    @Schema(description = "")
    private LocalDateTime updatedAt;
}