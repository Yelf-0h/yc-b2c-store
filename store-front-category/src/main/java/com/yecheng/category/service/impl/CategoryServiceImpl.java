package com.yecheng.category.service.impl;

import com.yecheng.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.category.mapper.CategoryMapper;
import com.yecheng.pojo.Category;
import com.yecheng.category.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2022-11-12 23:45:09
 */
@Service("categoryService")
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 根据类别名称查询类别对象
     *
     * @param categoryName 类别名称
     * @return {@link R}
     */
    @Override
    public R byName(String categoryName) {
        Category category = lambdaQuery().eq(Category::getCategoryName, categoryName).one();
        if (category == null){
            log.info("CategoryServiceImpl.byName业务结束，结果为：{}","类别查询失败！");
            return R.fail("类别查询失败！");
        }
        log.info("CategoryServiceImpl.byName业务结束，结果为：{}","类别查询成功！");
        return R.ok("类别查询成功！",category);
    }

    /**
     * 根据传入的热门类别名称集合!返回类别对应的id集合
     *
     * @param categoryName 类别名称集合
     * @return {@link R}
     */
    @Override
    public R hotsCategory(List<String> categoryName) {
        /* 封装查询参数，查询数据库 */
        List<Integer> ids = lambdaQuery().in(Category::getCategoryName, categoryName)
                .list().stream().map(Category::getCategoryId)
                .collect(Collectors.toList());
        R ok = R.ok("类别集合查询成功", ids);
        log.info("CategoryServiceImpl.hotsCategory业务结束，结果为：{}",ok);
        return ok;
    }

    /**
     * 查询类别列表进行返回
     *
     * @return {@link R}
     */
    @Override
    public R listCategory() {
        List<Category> list = list();
        R ok = R.ok("类别全部数据查询成功！", list);
        log.info("CategoryServiceImpl.listCategory业务结束，结果为：{}",ok);
        return ok;
    }
}
