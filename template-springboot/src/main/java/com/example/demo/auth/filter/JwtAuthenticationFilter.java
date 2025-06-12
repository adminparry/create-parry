package com.example.demo.auth.filter;

import com.example.demo.auth.config.SsoConfig;
import com.example.demo.auth.exception.SsoException;
import com.example.demo.utils.SsoUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private SsoUtil ssoUtil;


    @Autowired
    private SsoConfig ssoProperties;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if("options".equals(request.getMethod())){
            filterChain.doFilter(request, response);
        }
        if(ssoProperties.isInWhiteList(request)) {
            filterChain.doFilter(request, response);
            return;
        };

        String token = ssoUtil.getTokenFromRequest(request);

        try {
            Claims claims = ssoUtil.validateAndParseToken(token);
            // 将用户信息存入请求属性
            request.setAttribute("userId", claims.getSubject());
            request.setAttribute("username", claims.get("username"));
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "无效或过期的令牌");
            throw new SsoException("forbidden");
        }
    }
}
