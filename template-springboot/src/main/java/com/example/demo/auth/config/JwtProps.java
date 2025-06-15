package com.example.demo.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author: uf104259
 * @Date: 6/12/2025
 * @Description:
 */
@ConfigurationProperties(prefix = "app.jwt.web")
@Data
public class JwtProps {
    private boolean enable = true;


}
