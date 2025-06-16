package com.example.demo.crud.dto;

import com.example.demo.crud.entity.Users;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UsersDTO {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String realName;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UsersDTO(Users entity) {
        this.userId = entity.getUserId();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.realName = entity.getRealName();
        this.status = entity.getStatus();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

}
