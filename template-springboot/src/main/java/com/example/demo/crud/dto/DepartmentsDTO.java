package com.example.demo.crud.dto;

import com.example.demo.crud.entity.Departments;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DepartmentsDTO {
    private Integer deptId;
    private String deptName;
    private Integer parentId;
    private Integer orderNum;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DepartmentsDTO(Departments entity) {
        this.deptId = entity.getDeptId();
        this.deptName = entity.getDeptName();
        this.parentId = entity.getParentId();
        this.orderNum = entity.getOrderNum();
        this.status = entity.getStatus();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

}
