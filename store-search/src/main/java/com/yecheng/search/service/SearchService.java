package com.yecheng.search.service;

import com.yecheng.param.ProductSearchParam;
import com.yecheng.utils.R;

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
}
