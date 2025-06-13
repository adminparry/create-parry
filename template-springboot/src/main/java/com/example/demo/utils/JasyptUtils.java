package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

@Slf4j
public class JasyptUtils {

    /**
     * 加密
     * @param password 配置文件中设定的加密盐值
     * @param value 加密值
     * @return 加密后的字符串
     */
    public static String encrypt(String password,String value){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        return  encryptor.encrypt(value);
    }

    /**
     * 解密
     * @param password 配置文件中设定的加密盐值
     * @param value 解密密文
     * @return 解密后的字符串
     */
    public static String decrypt(String password,String value){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        return encryptor.decrypt(value);
    }

    /**
     * 配置,对应yml中的配置
     * @param password 盐值
     * @return SimpleStringPBEConfig
     */
    public static SimpleStringPBEConfig cryptor(String password){
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        //设置盐值
        config.setPassword(password);
        //设置算法
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;
    }


    public static void main(String[] args) {
        // 加密
        String encryptStr = encrypt("your_grace", "root");
        // 解密
        String decryptStr = decrypt("your_grace", "2dwq/1A3Izar5AxsXgp+uRKmyBN4NWkK");
//        log.info(encryptStr);
        log.info(decryptStr);
    }
}