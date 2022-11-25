package com.yecheng.category.controller;

import com.yecheng.category.service.CategoryService;
import com.yecheng.param.PageParam;
import com.yecheng.pojo.Category;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yelf
 * @create 2022-11-24-19:09
 */
@RestController
@RequestMapping("/category")
public class CategoryAdminController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/admin/list")
    public R pageCategory(@RequestBody PageParam pageParam){
        return categoryService.pageCategory(pageParam);
    }

    @PostMapping("/admin/save")
    public R saveCategory(@RequestBody Category category){
        return categoryService.saveCategory(category);
    }

    @PostMapping("/admin/remove")
    public R removeCategory(@RequestBody Integer categoryId){
        return categoryService.removeCategory(categoryId);
    }

    @PostMapping("/admin/update")
    public R updateCategory(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }
}
