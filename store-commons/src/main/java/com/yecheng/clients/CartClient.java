package com.yecheng.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yelf
 * @create 2022-11-25-16:30
 */
@FeignClient("cart-service")
public interface CartClient {

    /**
     * 检查购物车是否有正在使用该商品的
     * 后台管理模块删除商品服务调用
     *
     * @param productId 产品id
     * @return {@link Long}
     */
    @PostMapping("/cart/check")
    Long adminCheckCart(@RequestBody Integer productId);
}
