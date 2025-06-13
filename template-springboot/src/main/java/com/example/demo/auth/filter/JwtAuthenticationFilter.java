package com.example.demo.auth.filter;

import com.example.demo.auth.config.SsoConfig;
import com.example.demo.auth.exception.SsoException;
import com.example.demo.utils.*;
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
import java.io.Writer;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private SsoUtil ssoUtil;
    static volatile JsonUtil jsonUtil;


    private SsoConfig ssoProperties;

    public JwtAuthenticationFilter(SsoUtil ssoUtil, SsoConfig ssoProperties) {
        this.ssoUtil = ssoUtil;
        this.ssoProperties = ssoProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("\r\n========={}=============", TraceUtil.all());
        if("options".equals(request.getMethod())){
            filterChain.doFilter(request, response);
        }
        if(ssoProperties.isInWhiteList(request)) {
            filterChain.doFilter(request, response);
            return;
        };

        String token = ssoUtil.getTokenFromRequest(request);
        response.setContentType("application/json;charset=UTF-8");
        try {
            Claims claims = ssoUtil.validateAndParseToken(token);
            // 将用户信息存入请求属性
            request.setAttribute("userId", claims.getSubject());
            request.setAttribute("username", claims.get("username"));
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            log.info("{}", e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());


            StreamUtil.writeUnauthorized(e.getMessage(), response.getWriter());


        }
    }
}
