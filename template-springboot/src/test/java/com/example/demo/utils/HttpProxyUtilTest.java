package com.example.demo.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class HttpProxyUtilTest {

    @Value("${http.proxy.host}")
    private String proxyHost;

    @Value("${http.proxy.port}")
    private int proxyPort;

    @Test
    public void callApiWithProxy() {
        // 1. 准备请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0");
        headers.add("Authorization", "Bearer token123");

        // 2. GET请求示例
        String getUrl = "https://api.example.com/data";
        String getResponse = HttpProxyUtil.doGet(getUrl, headers, proxyHost, proxyPort);
        System.out.println("GET响应: " + getResponse);

        // 3. POST表单请求示例
        String postUrl = "https://api.example.com/submit";
        Map<String, String> formParams = new HashMap<>();
        formParams.put("username", "testuser");
        formParams.put("password", "testpass");

        String postResponse = HttpProxyUtil.doPost(postUrl, formParams, headers, proxyHost, proxyPort);
        System.out.println("POST响应: " + postResponse);

        // 4. POST JSON请求示例
        String jsonUrl = "https://api.example.com/json";
        String jsonBody = "{\"name\":\"John\", \"age\":30}";

        String jsonResponse = HttpProxyUtil.doPostJson(jsonUrl, jsonBody, headers, proxyHost, proxyPort);
        System.out.println("JSON响应: " + jsonResponse);
    }
}
