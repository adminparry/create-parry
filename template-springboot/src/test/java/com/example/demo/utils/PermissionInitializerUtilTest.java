package com.example.demo.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PermissionInitializerUtilTest {

    @Autowired
    private PermissionInitializerUtil  permissionInitializerUtil;

    @Test
    public void test(){

        permissionInitializerUtil.getAnnotatedBeans();
    }

}
