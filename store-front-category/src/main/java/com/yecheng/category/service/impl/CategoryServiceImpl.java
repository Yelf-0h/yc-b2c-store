package com.yecheng.category.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yecheng.clients.ProductClient;
import com.yecheng.param.PageParam;
import com.yecheng.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private ProductClient productClient;
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

    /**
     * 分类分页数据
     *
     * @param pageParam 页面参数
     * @return {@link R}
     */
    @Override
    public R pageCategory(PageParam pageParam) {
        Page<Category> page = page(new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize()));
        log.info("CategoryServiceImpl.pageCategory业务结束，结果为：{}","分类数据查询成功");
        return R.ok("分类数据查询成功！",page.getRecords(),page.getTotal());
    }

    /**
     * 添加类别
     *
     * @param category 类别
     * @return {@link R}
     */
    @Override
    public R saveCategory(Category category) {
        Category one = lambdaQuery().eq(Category::getCategoryName, category.getCategoryName()).one();
        if (one !=null){
            log.info("CategoryServiceImpl.saveCategory业务结束，结果为：{}","添加类别失败！已存在该类别");
            return R.fail("添加类别失败！已存在该类别");
        }
        boolean save = save(category);
        if (!save){
            log.info("CategoryServiceImpl.saveCategory业务结束，结果为：{}","添加类别失败！");
            return R.fail("添加类别失败！");
        }
        log.info("CategoryServiceImpl.saveCategory业务结束，结果为：{}","添加类别成功！");
        return R.ok("添加类别成功！");
    }

    /**
     * 删除类别
     *
     * @param categoryId 类别id
     * @return {@link R}
     */
    @Override
    public R removeCategory(Integer categoryId) {
        Long count = productClient.adminCategoryCount(categoryId);
        if (count > 0){
            log.info("CategoryServiceImpl.removeCategory业务结束，结果为：类别删除失败，被{}个商品使用中",count);
            return R.fail("删除类别失败，该类别在被"+count+"个商品使用中！");
        }
        boolean b = removeById(categoryId);
        if (!b) {
            return R.fail("删除类别失败！");
        }
        R ok = R.ok("删除类别成功！");
        log.info("CategoryServiceImpl.removeCategory业务结束，结果为：{}",ok);
        return ok;
    }

    /**
     * 更新类别
     *
     * @param category 类别
     * @return {@link R}
     */
    @Override
    public R updateCategory(Category category) {
        boolean b = updateById(category);
        if (!b){
            log.info("CategoryServiceImpl.updateCategory业务结束，结果为：{}","修改类别信息失败!");
            return R.fail("修改类别信息失败！");
        }
        log.info("CategoryServiceImpl.updateCategory业务结束，结果为：{}","修改类别信息成功!");
        return R.ok("修改类别信息成功！");
    }
}
