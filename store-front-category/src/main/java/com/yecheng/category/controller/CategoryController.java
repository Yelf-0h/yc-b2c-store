package com.yecheng.category.controller;

import com.yecheng.category.service.CategoryService;
import com.yecheng.param.ProductHotsParam;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yelf
 * @create 2022-11-12-23:49
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/promo/{categoryName}")
    public R byName(@PathVariable("categoryName") String categoryName){
        if (StringUtils.isEmpty(categoryName)){
            return R.fail("类别名称为空，无法查询类别数据");
        }
        return categoryService.byName(categoryName);
    }

    /**
     * 热门类别id查询
     *
     * @param hotsParam 热点参数
     * @param result    结果
     * @return {@link R}
     */
    @PostMapping("/hots")
    public R hotsCategory(@RequestBody @Validated ProductHotsParam hotsParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("类别集合查询失败！");
        }
        return categoryService.hotsCategory(hotsParam.getCategoryName());
    }

    /**
     * 查询类别列表进行返回
     *
     * @return {@link R}
     */
    @GetMapping("/list")
    public R listCategory(){
        return categoryService.listCategory();
    }
}
