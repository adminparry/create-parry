package com.example.demo.foundation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class IndexPoint {


    @Around("@annotation(com.example.demo.foundation.annotation.Index)")
    public Object anyName(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();


        System.out.println("aop 开始执行方法" + methodName);

        try {
            return joinPoint.proceed();
        } finally {
            System.out.println("aop 结束");
        }
    }
}
