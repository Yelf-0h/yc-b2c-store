package com.yecheng.search.controller;

import com.yecheng.param.ProductSearchParam;
import com.yecheng.search.service.SearchService;
import com.yecheng.utils.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
