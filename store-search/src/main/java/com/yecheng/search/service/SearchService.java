package com.yecheng.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yecheng.param.ProductSearchParam;
import com.yecheng.pojo.Product;
import com.yecheng.utils.R;

import java.io.IOException;

/**
 * @author Yelf
 * @create 2022-11-13-16:50
 */
public interface SearchService {
    /**
     * 搜索产品
     * 根据关键字和分页进行数据库数据查询搜索产品
     *
     * @param productSearchParam 产品搜索参数
     * @return {@link R}
     */
    R searchProduct(ProductSearchParam productSearchParam);

    /**
     * 保存产品
     * 商品同步：插入和更新
     *
     * @param product 产品
     * @return {@link R}
     * @throws IOException ioexception
     */
    R saveProduct(Product product) throws IOException;

    /**
     * 删除产品
     * 商品同步：删除
     *
     * @param productId 产品id
     * @return {@link R}
     */
    R removeProduct(Integer productId) throws IOException;
}
