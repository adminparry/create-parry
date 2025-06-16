package com.example.demo.crud.dto;

import com.example.demo.crud.entity.RolePermissions;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RolePermissionsDTO {
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
    private LocalDateTime createdAt;

    public RolePermissionsDTO(RolePermissions entity) {
        this.id = entity.getId();
        this.roleId = entity.getRoleId();
        this.permissionId = entity.getPermissionId();
        this.createdAt = entity.getCreatedAt();
    }

}
