package com.example.demo.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * 密码加盐哈希工具类（基于 PBKDF2 算法）
 */
public class PasswordSaltUtil {

    // 默认参数（可根据需求调整）
    private static final int DEFAULT_SALT_LENGTH = 16; // 盐长度（字节）
    private static final int DEFAULT_ITERATIONS = 10000; // 迭代次数
    private static final int DEFAULT_KEY_LENGTH = 256; // 哈希密钥长度（位）

    /**
     * 生成随机盐（密码学安全）
     * @param length 盐的字节长度（推荐 ≥16）
     * @return 十六进制字符串形式的盐
     */
    public static String generateSalt(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("盐长度必须大于0");
        }
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

    /**
     * 对密码进行加盐哈希（PBKDF2）
     * @param password 原始密码
     * @param salt 十六进制字符串形式的盐
     * @param iterations 迭代次数
     * @param keyLength 哈希密钥长度（位）
     * @return 十六进制字符串形式的哈希值
     */
    public static String hashPassword(String password, String salt, int iterations, int keyLength) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (salt == null || salt.isEmpty()) {
            throw new IllegalArgumentException("盐不能为空");
        }
        try {
            // 将十六进制盐转换为字节数组
            byte[] saltBytes = hexToBytes(salt);
            // 构建 PBEKeySpec（密码派生规范）
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    saltBytes,
                    iterations,
                    keyLength
            );
            // 使用 PBKDF2WithHmacSHA256 算法生成密钥
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hashBytes = skf.generateSecret(spec).getEncoded();
            // 转换为十六进制字符串
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("密码哈希失败", e);
        }
    }

    /**
     * 验证密码是否匹配（加盐哈希后）
     * @param rawPassword 原始密码
     * @param storedSalt 存储的盐（十六进制字符串）
     * @param storedHash 存储的哈希值（十六进制字符串）
     * @param iterations 迭代次数
     * @param keyLength 哈希密钥长度（位）
     * @return 匹配返回 true，否则 false
     */
    public static boolean verifyPassword(String rawPassword, String storedSalt, String storedHash,
                                         int iterations, int keyLength) {
        if (rawPassword == null || storedSalt == null || storedHash == null) {
            return false;
        }
        // 重新计算哈希值
        String computedHash = hashPassword(rawPassword, storedSalt, iterations, keyLength);
        // 安全比较（防止时间攻击）
        return constantTimeCompare(computedHash, storedHash);
    }

    // ------------------------- 工具方法 -------------------------

    /**
     * 字节数组转十六进制字符串（小写）
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 十六进制字符串转字节数组
     */
    private static byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }

    /**
     * 安全比较两个字符串（防止时间攻击）
     */
    private static boolean constantTimeCompare(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        int diff = 0;
        for (int i = 0; i < s1.length(); i++) {
            diff |= s1.charAt(i) ^ s2.charAt(i);
        }
        return diff == 0;
    }

    // ------------------------- 快捷方法（使用默认参数） -------------------------

    /**
     * 生成默认长度的盐（16字节）
     */
    public static String generateDefaultSalt() {
        return generateSalt(DEFAULT_SALT_LENGTH);
    }

    /**
     * 使用默认参数哈希密码（盐需自行传入）
     */
    public static String hashWithDefault(String password, String salt) {
        return hashPassword(password, salt, DEFAULT_ITERATIONS, DEFAULT_KEY_LENGTH);
    }

    /**
     * 验证密码（使用默认参数）
     */
    public static boolean verifyWithDefault(String rawPassword, String storedSalt, String storedHash) {
        return verifyPassword(rawPassword, storedSalt, storedHash, DEFAULT_ITERATIONS, DEFAULT_KEY_LENGTH);
    }
}