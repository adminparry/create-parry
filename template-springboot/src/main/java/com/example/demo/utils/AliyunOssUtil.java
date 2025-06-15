package com.example.demo.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * 阿里云OSS工具类
 */
public class AliyunOssUtil {
    private static final Logger logger = LoggerFactory.getLogger(AliyunOssUtil.class);

    private final String endpoint;
    private final String accessKeyId;
    private final String accessKeySecret;
    private final String bucketName;
    private final OSS ossClient;

    /**
     * 构造函数
     *
     * @param endpoint        端点地址，如 "https://oss-cn-hangzhou.aliyuncs.com"
     * @param accessKeyId     访问密钥ID
     * @param accessKeySecret 访问密钥
     * @param bucketName      存储桶名称
     */
    public AliyunOssUtil(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;

        // 创建OSSClient实例
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置最大连接数
        conf.setMaxConnections(200);
        // 设置请求超时时间
        conf.setConnectionTimeout(5000);
        // 设置Socket传输数据超时时间
        conf.setSocketTimeout(50000);

        this.ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);
    }

    /**
     * 上传文件到OSS
     *
     * @param objectName OSS中的对象名称（包含路径）
     * @param file       要上传的文件
     * @return 文件在OSS中的URL
     */
    public String uploadFile(String objectName, File file) {
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, file);
            ossClient.putObject(putObjectRequest);
            return generateUrl(objectName);
        } catch (Exception e) {
            logger.error("上传文件到OSS失败", e);
            throw new RuntimeException("上传文件到OSS失败", e);
        }
    }

    /**
     * 上传字节数组到OSS
     *
     * @param objectName OSS中的对象名称（包含路径）
     * @param bytes      字节数组
     * @return 文件在OSS中的URL
     */
    public String uploadBytes(String objectName, byte[] bytes) {
        try {
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
            return generateUrl(objectName);
        } catch (Exception e) {
            logger.error("上传字节数组到OSS失败", e);
            throw new RuntimeException("上传字节数组到OSS失败", e);
        }
    }

    /**
     * 上传输入流到OSS
     *
     * @param objectName OSS中的对象名称（包含路径）
     * @param inputStream 输入流
     * @return 文件在OSS中的URL
     */
    public String uploadStream(String objectName, InputStream inputStream) {
        try {
            ossClient.putObject(bucketName, objectName, inputStream);
            return generateUrl(objectName);
        } catch (Exception e) {
            logger.error("上传输入流到OSS失败", e);
            throw new RuntimeException("上传输入流到OSS失败", e);
        }
    }

    /**
     * 下载文件到本地
     *
     * @param objectName OSS中的对象名称（包含路径）
     * @param localFile  本地文件路径
     */
    public void downloadFile(String objectName, File localFile) {
        try {
            ossClient.getObject(new GetObjectRequest(bucketName, objectName), localFile);
        } catch (Exception e) {
            logger.error("从OSS下载文件失败", e);
            throw new RuntimeException("从OSS下载文件失败", e);
        }
    }

    /**
     * 获取文件输入流
     *
     * @param objectName OSS中的对象名称（包含路径）
     * @return 文件输入流
     */
    public InputStream getFileStream(String objectName) {
        try {
            OSSObject ossObject = ossClient.getObject(bucketName, objectName);
            return ossObject.getObjectContent();
        } catch (Exception e) {
            logger.error("获取OSS文件流失败", e);
            throw new RuntimeException("获取OSS文件流失败", e);
        }
    }

    /**
     * 删除文件
     *
     * @param objectName OSS中的对象名称（包含路径）
     */
    public void deleteFile(String objectName) {
        try {
            ossClient.deleteObject(bucketName, objectName);
        } catch (Exception e) {
            logger.error("删除OSS文件失败", e);
            throw new RuntimeException("删除OSS文件失败", e);
        }
    }

    /**
     * 批量删除文件
     *
     * @param objectNames 要删除的文件名列表
     */
    public void deleteFiles(List<String> objectNames) {
        try {
            DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName).withKeys(objectNames);
            ossClient.deleteObjects(request);
        } catch (Exception e) {
            logger.error("批量删除OSS文件失败", e);
            throw new RuntimeException("批量删除OSS文件失败", e);
        }
    }

    /**
     * 生成文件的访问URL（有效期默认1小时）
     *
     * @param objectName OSS中的对象名称（包含路径）
     * @return 文件的访问URL
     */
    public String generateUrl(String objectName) {
        return generateUrl(objectName, 3600);
    }

    /**
     * 生成文件的访问URL（带有效期）
     *
     * @param objectName OSS中的对象名称（包含路径）
     * @param expiration 过期时间（秒）
     * @return 文件的访问URL
     */
    public String generateUrl(String objectName, int expiration) {
        try {
            Date expirationDate = new Date(System.currentTimeMillis() + expiration * 1000);
            URL url = ossClient.generatePresignedUrl(bucketName, objectName, expirationDate);
            return url.toString();
        } catch (Exception e) {
            logger.error("生成OSS文件URL失败", e);
            throw new RuntimeException("生成OSS文件URL失败", e);
        }
    }

    /**
     * 检查文件是否存在
     *
     * @param objectName OSS中的对象名称（包含路径）
     * @return 是否存在
     */
    public boolean doesObjectExist(String objectName) {
        try {
            return ossClient.doesObjectExist(bucketName, objectName);
        } catch (Exception e) {
            logger.error("检查OSS文件是否存在失败", e);
            throw new RuntimeException("检查OSS文件是否存在失败", e);
        }
    }

    /**
     * 获取文件的MD5值
     *
     * @param objectName OSS中的对象名称（包含路径）
     * @return 文件的MD5值
     */
    public String getObjectMd5(String objectName) {
        try {
            ObjectMetadata metadata = ossClient.getObjectMetadata(bucketName, objectName);
            return metadata.getContentMD5();
        } catch (Exception e) {
            logger.error("获取OSS文件MD5失败", e);
            throw new RuntimeException("获取OSS文件MD5失败", e);
        }
    }

    /**
     * 关闭OSS客户端
     */
    public void shutdown() {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }

    /**
     * 计算本地文件的MD5（与OSS计算方式一致）
     *
     * @param bytes 文件字节数组
     * @return MD5字符串
     */
    public static String calculateMd5(byte[] bytes) {
        return BinaryUtil.toBase64String(BinaryUtil.calculateMd5(bytes));
    }
}