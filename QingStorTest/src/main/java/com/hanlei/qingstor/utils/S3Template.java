package com.hanlei.qingstor.utils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName S3Template
 * @Description
 * @Author hanlei
 * @Date 2020/5/27 5:55 下午
 * @Version 1.0
 */
@Component
public class S3Template {

    private static final Logger log = LoggerFactory.getLogger(S3Template.class);

    @Autowired
    private S3ConfigUtil s3ConfigUtil;

    public static final String RESULT = "result";
    public static final String PATH = "path";

    private AmazonS3 s3Client;

    private static String bucketName;

    //初始化
    @PostConstruct
    public void S3Template(){
        bucketName = s3ConfigUtil.getBucketName();
        AWSCredentials awsCredentials = new BasicAWSCredentials(s3ConfigUtil.getAccessKeyId(), s3ConfigUtil.getSecretAccessKeyId());
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        clientConfig.setMaxConnections(100);
        AmazonS3Client conn = new AmazonS3Client(awsCredentials, clientConfig);
        conn.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
        conn.setEndpoint(s3ConfigUtil.getUrl());
        this.s3Client = conn;
    }

    public void createBucketIfNotExists() {
        if (!(s3Client.doesBucketExist(bucketName))) {
            final Bucket bucket = s3Client.createBucket(new CreateBucketRequest(bucketName));
            log.info("bucket {} is not exists but has created , create time  {} ,owner {}", bucketName, bucket.getCreationDate(),
                    bucket.getOwner().getDisplayName());
        }

        String bucketLocation = s3Client.getBucketLocation(new GetBucketLocationRequest(bucketName));
        log.info("createBucketIfNotExists , bucket location {} ", bucketLocation);
    }

    public static boolean checkBucketExists(AmazonS3 s3, String bucketName) {
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket bucket : buckets) {
            if (Objects.equals(bucket.getName(), bucketName)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, Object> uploadFile(File tempFile, String s3KeyName, String md5) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3KeyName, tempFile);
//        putObjectRequest.withCannedAcl(CannedAccessControlList.Private);
        if(StringUtils.isNotBlank(md5)){
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentMD5(md5);
            putObjectRequest.setMetadata(metadata);
        }
        PutObjectResult putObjectResult = s3Client.putObject(putObjectRequest);
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, s3KeyName);
        URL url = s3Client.generatePresignedUrl(urlRequest);
        /*ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentMD5(md5);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3KeyName, tempFile);
        putObjectRequest.setMetadata(metadata);*/
        //设置权限属性等-非必需
//        putObjectRequest.setCannedAcl(null);
        //上传
//        PutObjectResult putObjectResult = s3Client.putObject(putObjectRequest);
        log.info("getETag", putObjectResult.getETag());
        log.info("uploaded File  [{}] to S3. url = [{}]", tempFile.getAbsolutePath(), url);
        Map<String, Object> map = new HashMap<>();
        map.put(RESULT,putObjectResult);
        map.put(PATH,url.getPath());
        return map;
    }

    public S3ObjectInputStream downFile(String key) {
        GetObjectRequest request = new GetObjectRequest(bucketName, key);
        S3Object object = s3Client.getObject(request);
        S3ObjectInputStream inputStream = object.getObjectContent();
        log.info("downloaded file [{}] from s3 , url {} , ", key, inputStream.getHttpRequest().getURI());
        return inputStream;
    }

    public void downFile(String key, String localPath) {
        GetObjectRequest request = new GetObjectRequest(bucketName, key);
        s3Client.getObject(request, new File(localPath));
    }

    public String getUrlFromS3(String s3KeyName) {
        GeneratePresignedUrlRequest httpRequest = new GeneratePresignedUrlRequest(bucketName, s3KeyName);
        String url = s3Client.generatePresignedUrl(httpRequest).toString();//临时链接
        return url;
    }

    public void deleteKeyFile(String s3KeyName) {
        s3Client.deleteObject(bucketName, s3KeyName);
    }
}
