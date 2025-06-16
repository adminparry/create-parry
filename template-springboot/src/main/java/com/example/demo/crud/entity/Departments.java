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
@TableName(value = "departments")
@Accessors(chain = true)
@Data
public class Departments extends BaseEntityUtil {

    @Schema(description = "")
    @TableId(type = IdType.ASSIGN_ID)
    private Integer deptId;

    @Schema(description = "")
    private String deptName;

    @Schema(description = "父部门ID")
    private Integer parentId;

    @Schema(description = "显示顺序")
    private Integer orderNum;

    @Schema(description = "1-启用, 0-禁用")
    private Integer status;

    @Schema(description = "")
    private LocalDateTime createdAt;

    @Schema(description = "")
    private LocalDateTime updatedAt;
}