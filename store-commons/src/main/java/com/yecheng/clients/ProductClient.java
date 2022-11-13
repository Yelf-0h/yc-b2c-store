package com.yecheng.clients;

import com.yecheng.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-11-13-15:25
 */
@FeignClient("product-service")
public interface ProductClient {

    /**
     * 搜索服务调用，查询全部商品信息
     * 进行同步
     * @return {@link List}<{@link Product}>
     */
    @GetMapping("/product/list")
    List<Product> allList();
}
