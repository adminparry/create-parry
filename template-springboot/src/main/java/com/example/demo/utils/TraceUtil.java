package com.example.demo.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class TraceUtil {
    private static int originStackIndex = 2;

    public static String getFileName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getFileName();
    }

    public static String getClassName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getClassName();
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getMethodName();
    }

    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[originStackIndex].getLineNumber();
    }
    public static String all(){
       String[] list = { getClassName(), getMethodName(), Integer.toString(getLineNumber())};
        return Arrays.stream(list).collect(Collectors.joining("; "));
    }

}
