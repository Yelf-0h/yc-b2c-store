package com.yecheng.clients;

import com.yecheng.param.ProductSearchParam;
import com.yecheng.pojo.Product;
import com.yecheng.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yelf
 * @create 2022-11-13-17:33
 */
@FeignClient("search-service")
public interface SearchClient {

    /**
     * 搜索产品
     *
     * @param productSearchParam 产品搜索参数
     * @return {@link R}
     */
    @PostMapping("/search/product")
    R searchProduct(@RequestBody ProductSearchParam productSearchParam);

    /**
     * 保存产品
     * 商品同步：插入和更新
     *
     * @param product 产品
     * @return {@link R}
     */
    @PostMapping("/search/save")
    R saveOrUpdateProduct(@RequestBody Product product);

    /**
     * 删除产品
     * 商品同步：删除
     *
     * @param productId 产品id
     * @return {@link R}
     */
    @PostMapping("/search/remove")
    R removeProduct(@RequestBody Integer productId);
}
