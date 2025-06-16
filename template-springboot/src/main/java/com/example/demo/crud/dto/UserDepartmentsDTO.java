package com.example.demo.crud.dto;

import com.example.demo.crud.entity.UserDepartments;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDepartmentsDTO {
    private Integer id;
    private Integer userId;
    private Integer deptId;
    private Integer isLeader;
    private LocalDateTime createdAt;

    public UserDepartmentsDTO(UserDepartments entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.deptId = entity.getDeptId();
        this.isLeader = entity.getIsLeader();
        this.createdAt = entity.getCreatedAt();
    }

}
