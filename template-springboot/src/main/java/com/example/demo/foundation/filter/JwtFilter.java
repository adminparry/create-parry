package com.example.demo.foundation.filter;
import com.auth0.jwt.interfaces.Claim;
import com.example.demo.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@WebFilter(filterName = "jwtFilter", urlPatterns = "/secure/*")
public class JwtFilter implements Filter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;


        response.setCharacterEncoding("UTF-8");

        final String token = request.getHeader("Authorization");
        if("OPTIONS".equals(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            if(token == null){
                response.getWriter().write("present token");
                return;
            }

//            Map<String, Claim> userData = jwtTokenUtil.validateToken(token);
//
//            if(userData == null){
//                response.getWriter().write("token invalid");
//                return;
//            }
//            log.info(userData.toString());
//            Integer id = userData.get("id").asInt();
//            String user = userData.get("user").asString();
//            String pass = userData.get("pass").asString();
//
//
//            request.setAttribute("id", id);
//            request.setAttribute("user", user);
//            request.setAttribute("pass", pass);

            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}