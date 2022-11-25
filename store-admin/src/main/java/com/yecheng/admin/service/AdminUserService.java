package com.yecheng.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.admin.param.AdminUserParam;
import com.yecheng.admin.pojo.AdminUser;
import com.yecheng.param.CartListParam;
import com.yecheng.param.PageParam;
import com.yecheng.param.ProductSaveParam;
import com.yecheng.param.ProductSearchParam;
import com.yecheng.pojo.Category;
import com.yecheng.pojo.Product;
import com.yecheng.pojo.User;
import com.yecheng.utils.R;

/**
 * (AdminUser)表服务接口
 *
 * @author makejava
 * @since 2022-11-24 01:23:13
 */
public interface AdminUserService extends IService<AdminUser> {

    /**
     * 登录
     *
     * @param adminUserParam 管理用户参数
     * @return {@link AdminUser}
     */
    AdminUser login(AdminUserParam adminUserParam);

    /**
     * 查询用户列表分页，调用user模块的接口
     *
     * @param pageParam 页面参数
     * @return {@link R}
     */
    R listUser(PageParam pageParam);

    /**
     * 删除用户
     * 根据用户id删除用户
     *
     * @param cartListParam 购物车列表参数
     * @return {@link R}
     */
    R removeUser(CartListParam cartListParam);

    /**
     * 更新用户信息
     *
     * @param user 用户
     * @return {@link R}
     */
    R updateUser(User user);

    /**
     * 添加用户
     *
     * @param user 用户
     * @return {@link R}
     */
    R saveUser(User user);

    /**
     * 类别分页数据
     *
     * @param pageParam 页面参数
     * @return {@link R}
     */
    R pageCategory(PageParam pageParam);

    /**
     * 保存类别
     *
     * @param category 类别
     * @return {@link R}
     */
    R saveCategory(Category category);

    /**
     * 删除类别
     *
     * @param categoryId 类别id
     * @return {@link R}
     */
    R removeCategory(Integer categoryId);

    /**
     * 更新类别
     *
     * @param category 类别
     * @return {@link R}
     */
    R updateCategory(Category category);

    /**
     * 产品列表,调用搜索服务
     *
     * @param productSearchParam 产品搜索参数
     * @return {@link R}
     */
    R listProduct(ProductSearchParam productSearchParam);

    /**
     * 保存产品,调用商品服务
     *
     * @param productSaveParam 产品保存参数
     * @return {@link R}
     */
    R saveProduct(ProductSaveParam productSaveParam);

    /**
     * 修改商品信息 调用商品服务
     *
     * @param product 产品
     * @return {@link R}
     */
    R updateProduct(Product product);

    /**
     * 删除商品，调用商品服务，商品服务调用购物车服务，订单服务，收藏服务
     *
     * @param productId 产品id
     * @return {@link R}
     */
    R removeProduct(Integer productId);

    /**
     * 分页查询订单数据，调用订单模块
     *
     * @param pageParam 页面参数
     * @return {@link R}
     */
    R pageOrder(PageParam pageParam);
}
