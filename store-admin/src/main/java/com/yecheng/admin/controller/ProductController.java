package com.yecheng.admin.controller;

import com.yecheng.admin.service.AdminUserService;
import com.yecheng.admin.utils.AliyunOssUtils;
import com.yecheng.admin.utils.KodoOssUtils;
import com.yecheng.param.ProductSaveParam;
import com.yecheng.param.ProductSearchParam;
import com.yecheng.pojo.Product;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Yelf
 * @create 2022-11-25-0:25
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AliyunOssUtils aliyunOssUtils;
    @Autowired
    private KodoOssUtils kodoOssUtils;
    /**
     * 产品列表，调用搜索服务
     *
     * @param productSearchParam 产品搜索参数
     * @return {@link R}
     */
    @GetMapping("/list")
    public R listProduct(ProductSearchParam productSearchParam){
        return adminUserService.listProduct(productSearchParam);
    }

    @PostMapping("/upload")
    public R adminUpload(MultipartFile img) throws Exception {
        /*获取文件名,前面加上uuid保持唯一*/
        /*String filename = img.getOriginalFilename();
        filename = UUID.randomUUID().toString().replaceAll("-","")+"-"+filename;
        String contentType = img.getContentType();
        byte[] bytes = img.getBytes();
        int hours = 1000;
        String url = aliyunOssUtils.uploadImage(filename, bytes, contentType, hours);
        System.out.println("url = "+url);
        return R.ok("图片上传成功！",url);*/
        /* ===============================以上使用的是阿里云oss，浪费钱，换成七牛云oss========================= */
        String filename = img.getOriginalFilename();
        filename = UUID.randomUUID().toString().replaceAll("-","")+"-"+filename;
        byte[] bytes = img.getBytes();
        String url = kodoOssUtils.uploadImage(filename, bytes);
        System.out.println("url = "+url);
        return R.ok("图片上传成功！",url);
    }

    @PostMapping("/save")
    public R saveProduct(ProductSaveParam productSaveParam){
        return adminUserService.saveProduct(productSaveParam);
    }

    @PostMapping("/update")
    public R updateProduct(Product product){
        return adminUserService.updateProduct(product);
    }
    @PostMapping("/remove")
    public R removeProduct(Integer productId){
        return adminUserService.removeProduct(productId);
    }
}
