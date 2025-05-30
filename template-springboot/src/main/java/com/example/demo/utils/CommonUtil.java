package com.example.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

@Component
public class CommonUtil {

    private final ObjectMapper objectMapper;

    public CommonUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 生成UUID
     */
    public String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * MD5加密
     */
    public String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /**
     * Base64编码
     */
    public String base64Encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64解码
     */
    public String base64Decode(String input) {
        return new String(Base64.getDecoder().decode(input), StandardCharsets.UTF_8);
    }

    /**
     * 对象转JSON字符串
     */
    public String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * JSON字符串转对象
     */
    public <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

    /**
     * 获取当前时间字符串
     */
    public String getCurrentDateTime(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(
            StringUtils.defaultIfBlank(pattern, "yyyy-MM-dd HH:mm:ss")));
    }

    /**
     * 检查字符串是否为空
     */
    public boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    /**
     * 检查字符串是否不为空
     */
    public boolean isNotEmpty(String str) {
        return StringUtils.isNotEmpty(str);
    }

    /**
     * 检查字符串是否为空白
     */
    public boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 检查字符串是否不为空白
     */
    public boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 截取字符串
     */
    public String substring(String str, int start, int end) {
        return StringUtils.substring(str, start, end);
    }

    /**
     * 字符串左填充
     */
    public String leftPad(String str, int size, char padChar) {
        return StringUtils.leftPad(str, size, padChar);
    }

    /**
     * 字符串右填充
     */
    public String rightPad(String str, int size, char padChar) {
        return StringUtils.rightPad(str, size, padChar);
    }
} 