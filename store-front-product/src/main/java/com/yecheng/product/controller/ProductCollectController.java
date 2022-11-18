package com.yecheng.product.controller;

import com.yecheng.param.ProductCollectParam;
import com.yecheng.product.service.ProductService;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yelf
 * @create 2022-11-17-16:19
 */
@RestController
@RequestMapping("/product")
public class ProductCollectController {

    @Autowired
    private ProductService productService;

    @PostMapping("/collect/list")
    public R productIds(@RequestBody @Validated ProductCollectParam productCollectParam, BindingResult result){
        if (result.hasErrors()){
            return R.ok("没有收藏数据！");
        }
        return productService.productIds(productCollectParam.getProductIds());
    }

}
