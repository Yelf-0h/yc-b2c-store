package com.yecheng.admin.service.impl;

import com.yecheng.admin.param.AdminUserParam;
import com.yecheng.clients.*;
import com.yecheng.constants.UserConstants;
import com.yecheng.param.CartListParam;
import com.yecheng.param.PageParam;
import com.yecheng.param.ProductSaveParam;
import com.yecheng.param.ProductSearchParam;
import com.yecheng.pojo.Category;
import com.yecheng.pojo.Product;
import com.yecheng.pojo.User;
import com.yecheng.utils.MD5Util;
import com.yecheng.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.admin.mapper.AdminUserMapper;
import com.yecheng.admin.pojo.AdminUser;
import com.yecheng.admin.service.AdminUserService;

/**
 * (AdminUser)表服务实现类
 *
 * @author makejava
 * @since 2022-11-24 01:23:13
 */
@Service("adminUserService")
@Slf4j
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private SearchClient searchClient;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private OrderClient orderClient;

    /**
     * 登录
     *
     * @param adminUserParam 管理用户参数
     * @return {@link AdminUser}
     */
    @Override
    public AdminUser login(AdminUserParam adminUserParam) {
        /* 查询是否有此账号密码的用户存在，注意：密码需要先md5加密加盐后才能作为查询条件 */
        AdminUser adminUser = lambdaQuery().eq(AdminUser::getUserAccount, adminUserParam.getUserAccount())
                .eq(AdminUser::getUserPassword, MD5Util.encode(adminUserParam.getUserPassword()+UserConstants.USER_SALT))
                .one();
        log.info("AdminUserServiceImpl.login业务结束，结果为：{}",adminUser);
        return adminUser;
    }

    /**
     * 查询用户列表分页，调用user模块的接口
     *
     * @param pageParam 页面参数
     * @return {@link R}
     */
    @Cacheable(value = "list.user",key = "#pageParam.currentPage+'-'+#pageParam.pageSize")
    @Override
    public R listUser(PageParam pageParam) {
        R r = userClient.adminPageUser(pageParam);
        log.info("AdminUserServiceImpl.listUser业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 删除用户
     * 根据用户id删除用户
     *
     * @param cartListParam 购物车列表参数
     * @return {@link R}
     */
    @CacheEvict(value = "list.user",allEntries = true)
    @Override
    public R removeUser(CartListParam cartListParam) {
        R r = userClient.adminRemoveUser(cartListParam);
        log.info("AdminUserServiceImpl.removeUser业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 更新用户信息
     *
     * @param user 用户
     * @return {@link R}
     */
    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "list.user",allEntries = true)
            }
    )
    public R updateUser(User user) {

        R r = userClient.adminUpdateUser(user);
        log.info("AdminUserServiceImpl.updateUser业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 添加用户
     *
     * @param user 用户
     * @return {@link R}
     */
    @Override
    @CacheEvict(value = "list.user",allEntries = true)
    public R saveUser(User user) {
        R r = userClient.adminSaveUser(user);
        log.info("AdminUserServiceImpl.saveUser业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 类别分页数据
     *
     * @param pageParam 页面参数
     * @return {@link R}
     */
    @Override
    @Cacheable(value = "list.category",key = "#pageParam.currentPage+'-'+#pageParam.pageSize")
    public R pageCategory(PageParam pageParam) {
        R r = categoryClient.adminPageCategory(pageParam);
        log.info("AdminUserServiceImpl.pageCategory业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 保存类别
     *
     * @param category 类别
     * @return {@link R}
     */
    @Override
    @CacheEvict(value = "list.category",allEntries = true)
    public R saveCategory(Category category) {
        R r = categoryClient.adminSaveCategory(category);
        log.info("AdminUserServiceImpl.saveCategory业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 删除类别
     *
     * @param categoryId 类别id
     * @return {@link R}
     */
    @Override
    @CacheEvict(value = "list.category",allEntries = true)
    public R removeCategory(Integer categoryId) {
        R r = categoryClient.adminRemoveCategory(categoryId);
        log.info("AdminUserServiceImpl.removeCategory业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 更新类别
     *
     * @param category 类别
     * @return {@link R}
     */
    @Override
    @CacheEvict(value = "list.category",allEntries = true)
    public R updateCategory(Category category) {
        R r = categoryClient.adminUpdateCategory(category);
        log.info("AdminUserServiceImpl.updateCategory业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 产品列表,调用搜索服务
     *
     * @param productSearchParam 产品搜索参数
     * @return {@link R}
     */
    @Override
    public R listProduct(ProductSearchParam productSearchParam) {
        R r = searchClient.searchProduct(productSearchParam);
        log.info("AdminUserServiceImpl.listProduct业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 保存产品,调用商品服务
     *
     * @param productSaveParam 产品保存参数
     * @return {@link R}
     */
    @Override
    @CacheEvict(value = "list.product",allEntries = true)
    public R saveProduct(ProductSaveParam productSaveParam) {
        R r = productClient.adminSaveProduct(productSaveParam);
        log.info("AdminUserServiceImpl.saveProduct业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 修改商品信息 调用商品服务
     *
     * @param product 产品
     * @return {@link R}
     */
    @Override
    @CacheEvict(value = "list.product",allEntries = true)
    public R updateProduct(Product product) {
        R r = productClient.adminUpdateProduct(product);
        log.info("AdminUserServiceImpl.updateProduct业务结束，结果为：{}",r);
        return r;

    }

    /**
     * 删除商品，调用商品服务，商品服务调用购物车服务，订单服务，收藏服务
     *
     * @param productId 产品id
     * @return {@link R}
     */
    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "list.product",allEntries = true),
                    @CacheEvict(value = "product",key = "#productId")
            }
    )
    public R removeProduct(Integer productId) {
        R r = productClient.adminRemoveProduct(productId);
        log.info("AdminUserServiceImpl.removeProduct业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 分页查询订单数据，调用订单模块
     *
     * @param pageParam 页面参数
     * @return {@link R}
     */
    @Override
    public R pageOrder(PageParam pageParam) {
        R r = orderClient.adminPageOrder(pageParam);
        log.info("AdminUserServiceImpl.pageOrder业务结束，结果为：{}",r);
        return r;
    }
}

