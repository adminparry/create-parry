package com.example.demo.utils;

import com.example.demo.utils.UserContextUtil;
import com.example.demo.foundation.annotation.Index;
import com.example.demo.foundation.annotation.Permission;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

@Component
public class InterceptorUtil implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Pre Handle method is Calling");
        String uid = UserContextUtil.getUid();
        System.out.println(uid);
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Class<?> clazz = method.getDeclaringClass();

            Index code = method.getAnnotation(Index.class);
            Permission code1 = clazz.getAnnotation(Permission.class);

            if(Objects.nonNull(code) && Objects.nonNull(code1)){
                String code_ = Arrays.toString(code.value());
                String code_1 = Arrays.toString(code1.code());

                System.out.println(code_ + "." + code_1);
            }
        }
        return true; // 返回false则中断执行
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        // 在控制器方法执行后，视图渲染前调用
        System.out.println("Post Handle method is Calling");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        // 在整个请求完成后调用
        System.out.println("Request and Response is completed");
    }

}
