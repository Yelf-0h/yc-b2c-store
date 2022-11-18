package com.yecheng.product.controller;

import com.yecheng.param.ProductCollectParam;
import com.yecheng.param.ProductIdParam;
import com.yecheng.param.ProductIdsParam;
import com.yecheng.pojo.Product;
import com.yecheng.product.service.ProductService;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yelf
 * @create 2022-11-18-16:03
 */
@RestController
@RequestMapping("/product")
public class ProductCartController {
    @Autowired
    private ProductService productService;

    @PostMapping("/cart/detail")
    public Product cartDetail(@RequestBody @Validated ProductIdParam productIdParam, BindingResult result){
        if (result.hasErrors()){
            return null;
        }
        R detail = productService.detail(productIdParam.getProductID());
        Product product = (Product) detail.getData();
        return product;
    }

    @PostMapping("/cart/list")
    public List<Product> cartList(@RequestBody @Validated ProductCollectParam param, BindingResult result){
        if (result.hasErrors()) {
            return new ArrayList<Product>();
        }
        return productService.cartList(param.getProductIds());
    }

}
