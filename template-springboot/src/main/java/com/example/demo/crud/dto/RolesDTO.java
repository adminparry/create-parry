package com.example.demo.crud.dto;

import com.example.demo.crud.entity.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RolesDTO {
    private Integer roleId;
    private String roleName;
    private String description;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RolesDTO(Roles entity) {
        this.roleId = entity.getRoleId();
        this.roleName = entity.getRoleName();
        this.description = entity.getDescription();
        this.status = entity.getStatus();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

}
