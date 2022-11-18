package com.yecheng.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.param.ProductHotsParam;
import com.yecheng.param.ProductIdsParam;
import com.yecheng.param.ProductSearchParam;
import com.yecheng.pojo.Product;
import com.yecheng.utils.R;

import java.util.List;

/**
 * (Product)表服务接口
 *
 * @author makejava
 * @since 2022-11-12 23:42:35
 */
public interface ProductService extends IService<Product> {

    /**
     * 单类别名称 查询热门商品 至多7条
     *
     * @param categoryName 类别名称
     * @return {@link R}
     */
    R promoList(String categoryName);

    /**
     * 热点列表
     * 多类别热门商品查询 根据类别名称集合! 至多查询7条!
     * 1. 调用类别服务
     * 2. 类别集合id查询商品
     * 3. 结果集封装即可
     *
     * @param productHotsParam 产品还有参数
     * @return {@link R}
     */
    R hotsList(ProductHotsParam productHotsParam);

    /**
     * 查询类别列表进行返回
     *
     * @return {@link R}
     */
    R categoryList();

    /**
     * 类别商品查询 前端传递类别集合
     *
     * @param productIdsParam 产品id参数
     * @return {@link R}
     */
    R byCategory(ProductIdsParam productIdsParam);

    /**
     * 查询商品详情根据商品id
     *
     * @param productID 产品id
     * @return {@link R}
     */
    R detail(Integer productID);

    /**
     * 查询商品图片详情根据商品id
     *
     * @param productID 产品id
     * @return {@link R}
     */
    R pictures(Integer productID);

    /**
     * 搜索服务调用，查询全部商品信息
     * 进行同步
     * @return {@link List}<{@link Product}>
     */
    List<Product> allList();

    /**
     * 搜索业务，需要调用搜索服务
     *
     * @param productSearchParam 产品搜索参数
     * @return {@link R}
     */
    R search(ProductSearchParam productSearchParam);

    /**
     * 根据产品id集合查询商品信息
     *
     * @param productIds 产品id
     * @return {@link R}
     */
    R productIds(List<Integer> productIds);

    /**
     * 购物车列表,根据ids查询商品集合
     *
     * @param productIds 产品id
     * @return {@link List}<{@link Product}>
     */
    List<Product> cartList(List<Integer> productIds);
}
