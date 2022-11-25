package com.yecheng.admin.controller;

import com.yecheng.admin.service.AdminUserService;
import com.yecheng.param.PageParam;
import com.yecheng.pojo.Category;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yelf
 * @create 2022-11-24-19:24
 * 调用category的控制器
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping("/list")
    public R pageCategory(PageParam pageParam){
        return adminUserService.pageCategory(pageParam);
    }

    @PostMapping("/save")
    public R saveCategory(Category category){
        return adminUserService.saveCategory(category);
    }
    @PostMapping("/remove")
    public R removeCategory(Integer categoryId){
        return adminUserService.removeCategory(categoryId);
    }

    @PostMapping("/update")
    public R updateCategory(Category category){
        return adminUserService.updateCategory(category);
    }
}
