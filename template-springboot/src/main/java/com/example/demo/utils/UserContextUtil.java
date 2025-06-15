package com.example.demo.utils;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public class UserContextUtil {

    private static final ThreadLocal<Map<String,  Object>> USER_LOCAL = new NamedInheritableThreadLocal<>("userLocal");



    public static void set(Map<String,Object> userDetail) {
        USER_LOCAL.set(userDetail);
    }
    public static Map<String,Object> get() {
        return USER_LOCAL.get();
    }
    public static List<String> getPermissions(){
        return  null;
    }
    public static String getUid(){
        Map<String, Object>  userDetail  = USER_LOCAL.get();
        return CollectionUtils.isEmpty(userDetail) ? null : userDetail.get("uid").toString();

    }
    public static void clear(){
        USER_LOCAL.remove();
    }
}
