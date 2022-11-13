package com.yecheng.product.controller;

import com.yecheng.pojo.Product;
import com.yecheng.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-11-13-15:16
 * 搜索服务调用的controller
 */
@RestController
@RequestMapping("/product")
public class ProductSearchController {

    @Autowired
    private ProductService productService;

    /**
     * 搜索服务调用，查询全部商品信息
     * 进行同步
     *
     * @return {@link List}<{@link Product}>
     */
    @GetMapping("/list")
    public List<Product> allList(){
        return productService.allList();
    }
}
