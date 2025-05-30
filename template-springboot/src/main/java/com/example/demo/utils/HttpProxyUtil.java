package com.example.demo.utils;

import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;

/**
 * HTTP代理工具类
 */
public class HttpProxyUtil {

    /**
     * 发送GET请求（带代理）
     * @param url 请求地址
     * @param headers 请求头
     * @param proxyHost 代理主机
     * @param proxyPort 代理端口
     * @return 响应结果
     */
    public static String doGet(String url, HttpHeaders headers, String proxyHost, int proxyPort) {
        RestTemplate restTemplate = createRestTemplateWithProxy(proxyHost, proxyPort);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    /**
     * 发送POST请求（带代理）
     * @param url 请求地址
     * @param params 表单参数
     * @param headers 请求头
     * @param proxyHost 代理主机
     * @param proxyPort 代理端口
     * @return 响应结果
     */
    public static String doPost(String url, Map<String, String> params, HttpHeaders headers,
                                String proxyHost, int proxyPort) {
        RestTemplate restTemplate = createRestTemplateWithProxy(proxyHost, proxyPort);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        params.forEach(formData::add);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return response.getBody();
    }

    /**
     * 发送JSON格式POST请求（带代理）
     * @param url 请求地址
     * @param jsonBody JSON请求体
     * @param headers 请求头
     * @param proxyHost 代理主机
     * @param proxyPort 代理端口
     * @return 响应结果
     */
    public static String doPostJson(String url, String jsonBody, HttpHeaders headers,
                                    String proxyHost, int proxyPort) {
        RestTemplate restTemplate = createRestTemplateWithProxy(proxyHost, proxyPort);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return response.getBody();
    }

    /**
     * 创建带代理的RestTemplate
     */
    private static RestTemplate createRestTemplateWithProxy(String proxyHost, int proxyPort) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        requestFactory.setProxy(proxy);
        requestFactory.setConnectTimeout(5000);  // 5秒连接超时
        requestFactory.setReadTimeout(10000);    // 10秒读取超时

        return new RestTemplate(requestFactory);
    }
}