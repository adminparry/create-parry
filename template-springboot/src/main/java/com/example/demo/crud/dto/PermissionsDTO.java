package com.example.demo.crud.dto;

import com.example.demo.crud.entity.Permissions;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PermissionsDTO {
    private Integer permissionId;
    private String permissionName;
    private String permissionCode;
    private String description;
    private String resourceType;
    private String resourcePath;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PermissionsDTO(Permissions entity) {
        this.permissionId = entity.getPermissionId();
        this.permissionName = entity.getPermissionName();
        this.permissionCode = entity.getPermissionCode();
        this.description = entity.getDescription();
        this.resourceType = entity.getResourceType();
        this.resourcePath = entity.getResourcePath();
        this.status = entity.getStatus();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

}
