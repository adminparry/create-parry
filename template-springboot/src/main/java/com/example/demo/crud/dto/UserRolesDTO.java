package com.example.demo.crud.dto;

import com.example.demo.crud.entity.UserRoles;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserRolesDTO {
    private Integer id;
    private Integer userId;
    private Integer roleId;
    private LocalDateTime createdAt;

    public UserRolesDTO(UserRoles entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.roleId = entity.getRoleId();
        this.createdAt = entity.getCreatedAt();
    }

}
