package com.yecheng.clients;

import com.yecheng.param.ProductCollectParam;
import com.yecheng.param.ProductIdParam;
import com.yecheng.pojo.Product;
import com.yecheng.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    /**
     *
     * 根据产品id集合查询商品信息
     *
     * @param productCollectParam 收集产品参数
     * @return {@link R}
     */
    @PostMapping("/product/collect/list")
    R productIds(@RequestBody ProductCollectParam productCollectParam);

    /**
     * 根据id获取购物车商品详情
     *
     * @param productIdParam 产品id参数
     * @return {@link Product}
     */
    @PostMapping("/product/cart/detail")
    Product cartDetail(@RequestBody ProductIdParam productIdParam);

    /**
     * 购物车列表,根据ids查询商品信息集合
     *
     * @param param 参数
     * @return {@link List}<{@link Product}>
     */
    @PostMapping("/product/cart/list")
    List<Product> cartList(@RequestBody ProductCollectParam param);
}
