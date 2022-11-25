package com.yecheng.product.controller;

import com.yecheng.param.ProductSaveParam;
import com.yecheng.pojo.Product;
import com.yecheng.product.service.ProductService;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yelf
 * @create 2022-11-24-22:44
 */
@RestController
@RequestMapping("/product")
public class ProductAdminController {

    @Autowired
    private ProductService productService;

    /**
     * 类别服务调用 再后台管理调用类别服务
     *
     * @param categoryId 类别id
     * @return {@link Long}
     */
    @PostMapping("/admin/count")
    public Long categoryCount(@RequestBody Integer categoryId){
        return productService.categoryCount(categoryId);
    }

    @PostMapping("/admin/save")
    public R saveProduct(@RequestBody ProductSaveParam productSaveParam){
        return productService.saveProduct(productSaveParam);
    }

    @PostMapping("/admin/update")
    public R updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

    @PostMapping("/admin/remove")
    public R removeProduct(@RequestBody Integer productId){
        return productService.removeProduct(productId);
    }

}
