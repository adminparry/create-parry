package com.example.demo.auth.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("jwt.header")
    private String header;

    @Value("jwt.token-prefix")
    private String prefix;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String auth  = request.getHeader(header);

        String username = null;
        String jwt =  null;

        if(null != auth && auth.startsWith(prefix)){
            jwt = auth.substring(prefix.length());

        }
        if(null != username && null != SecurityContextHolder.getContext().getAuthentication()){
            UserDetails userDetails =  this.userDetailsService.loadUserByUsername(username);

        }

        filterChain.doFilter(request, response);
    }
}
