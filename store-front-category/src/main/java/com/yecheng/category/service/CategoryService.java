package com.yecheng.category.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.pojo.Category;
import com.yecheng.utils.R;

import java.util.List;

/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2022-11-12 23:45:09
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根据类别名称查询类别对象
     *
     * @param categoryName 类别名称
     * @return {@link R}
     */
    R byName(String categoryName);

    /**
     * 根据传入的热门类别名称集合!返回类别对应的id集合
     *
     * @param categoryName 类别名称集合
     * @return {@link R}
     */
    R hotsCategory(List<String> categoryName);

    /**
     * 查询类别列表进行返回
     *
     * @return {@link R}
     */
    R listCategory();
}
