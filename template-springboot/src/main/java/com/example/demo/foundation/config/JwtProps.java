package com.example.demo.foundation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
