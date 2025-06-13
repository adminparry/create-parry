package com.example.demo.utils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AesUtil {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int KEY_SIZE = 256;
    private static final int IV_SIZE = 16;

    public AesUtil() {
    }

    public static class CryptoException extends RuntimeException {
        public CryptoException(String message) {
            super(message);
        }

        public CryptoException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static String encrypt(String data, String secret) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "AES");
            SecureRandom randomSecureRandom = new SecureRandom();
            byte[] iv = new byte[16];
            randomSecureRandom.nextBytes(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(1, secretKeySpec, ivParameterSpec);
            byte[] d = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            byte[] ret = combineIv(iv, d);
            return Base64.getEncoder().encodeToString(ret);
        } catch (Exception var10) {
            Exception e = var10;
            throw new CryptoException(e.getMessage(), e);
        }
    }

    public static String decrypt(String data, String secret) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] b = Base64.getDecoder().decode(data);
            byte[] iv = getIv(b);
            byte[] d = getData(b);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(2, secretKeySpec, ivParameterSpec);
            byte[] ret = cipher.doFinal(d);
            return new String(ret);
        } catch (Exception var10) {
            Exception e = var10;
            throw new CryptoException(e.getMessage(), e);
        }
    }

    public static String encrypt(String data, String secret, String iv) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(1, secretKeySpec, ivParameterSpec);
            byte[] d = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(d);
        } catch (Exception var8) {
            Exception e = var8;
            throw new CryptoException(e.getMessage(), e);
        }
    }

    public static String decrypt(String data, String secret, String iv) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] d = Base64.getDecoder().decode(data);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(2, secretKeySpec, ivParameterSpec);
            byte[] ret = cipher.doFinal(d);
            return new String(ret);
        } catch (Exception var9) {
            Exception e = var9;
            throw new CryptoException(e.getMessage(), e);
        }
    }

    private static byte[] combineIv(byte[] iv, byte[] data) {
        byte[] ret = new byte[iv.length + data.length];
        System.arraycopy(iv, 0, ret, 0, iv.length);
        System.arraycopy(data, 0, ret, iv.length, data.length);
        return ret;
    }

    private static byte[] getIv(byte[] b) {
        byte[] iv = new byte[16];
        System.arraycopy(b, 0, iv, 0, 16);
        return iv;
    }

    private static byte[] getData(byte[] b) {
        byte[] data = new byte[b.length - 16];
        System.arraycopy(b, 16, data, 0, b.length - 16);
        return data;
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}
