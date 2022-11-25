package com.yecheng.admin.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author Yelf
 * @create 2022-11-25-23:29
 */
@Component
@Data
@ConfigurationProperties(prefix = "kodo.oss.file")
@Slf4j
public class KodoOssUtils {

    /**
     * ...生成上传凭证，然后准备上传
     */
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String baseUrl;
    /**
     * 默认不指定key的情况下，以文件内容的hash值作为文件名
     */
    private String key;

    public String uploadImage(String objectName, byte[] content){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration();
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        UploadManager uploadManager = new UploadManager(cfg);
        key = objectName;
        byte[] uploadBytes = content;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("KodoOssUtils.uploadImage业务结束，结果为：{}",putRet.key);
            log.info("KodoOssUtils.uploadImage业务结束，结果为：{}",putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error("KodoOssUtils.uploadImage业务出错，结果为：{}",r.toString());
            try {
                log.error("KodoOssUtils.uploadImage业务出错，结果为：{}",r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            return "上传失败！";
        }
        return baseUrl+key;
    }



}
