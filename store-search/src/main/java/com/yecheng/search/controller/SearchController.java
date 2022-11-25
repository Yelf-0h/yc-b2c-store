package com.yecheng.search.controller;

import com.yecheng.param.ProductSearchParam;
import com.yecheng.pojo.Product;
import com.yecheng.search.service.SearchService;
import com.yecheng.utils.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Yelf
 * @create 2022-11-13-16:46
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @PostMapping("/product")
    public R searchProduct(@RequestBody ProductSearchParam productSearchParam){
        return searchService.searchProduct(productSearchParam);
    }

    @PostMapping("/save")
    public R saveProduct(@RequestBody Product product) throws IOException {
        return searchService.saveProduct(product);
    }

    @PostMapping("/remove")
    public R removeProduct(@RequestBody Integer productId) throws IOException {
        return searchService.removeProduct(productId);
    }
}
