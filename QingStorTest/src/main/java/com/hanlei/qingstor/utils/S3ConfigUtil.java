package com.hanlei.qingstor.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName S3ConfigUtil
 * @Description
 * @Author hanlei
 * @Date 2020/5/27 4:05 下午
 * @Version 1.0
 */
@Component
public class S3ConfigUtil {

    //青云accessKeyId
    @Value("${qingStor.qy_access_key_id}")
    private String accessKeyId;
    //青云qy_secret_access_key
    @Value("${qingStor.qy_secret_access_key}")
    private String secretAccessKeyId;
    //bucketname
    @Value("${qingStor.bucket}")
    private String bucketName;
    //上传区域
    @Value("${qingStor.zone}")
    private String zone;
    //上传云地址
    @Value("${qingStor.url}")
    private String url;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getSecretAccessKeyId() {
        return secretAccessKeyId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getZone() {
        return zone;
    }

    public String getUrl() {
        return url;
    }
}
