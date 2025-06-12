package com.example.demo.auth.controller;

import com.example.demo.auth.entity.User;
import com.example.demo.auth.service.AuthUserService;
import com.example.demo.utils.SsoUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private SsoUtil ssoUtil;

    @Autowired(required = true)
    private AuthUserService authUserService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception {

        // 1. 验证用户名密码
        User user = authUserService.authenticate(request.getUsername(), request.getPassword());

        // 2. 生成令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRole());
        String token = ssoUtil.generateToken(user.getId().toString(), user.getUsername(), claims);

        // 3. 返回响应
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = ssoUtil.getTokenFromRequest(request);
        ssoUtil.invalidateToken(token);
        return ResponseEntity.ok().build();
    }
}


@Data
class LoginRequest {
    private String username;
    private String password;
}

@Data
class RegisterRequest {
    private String username;
    private String password;
}

@Data
class LogoutRequest {
    private String username;
}
