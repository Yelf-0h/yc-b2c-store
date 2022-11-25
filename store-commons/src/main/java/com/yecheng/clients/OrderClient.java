package com.yecheng.clients;

import com.yecheng.param.PageParam;
import com.yecheng.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yelf
 * @create 2022-11-25-16:37
 */
@FeignClient("order-service")
public interface OrderClient {

    /**
     * 检查订单中是否有使用到该商品的订单
     * 后台管理模块删除商品服务调用
     *
     * @param productId 产品id
     * @return {@link Long}
     */
    @PostMapping("/order/check")
    Long adminCheckOrder(@RequestBody Integer productId);

    /**
     * 分页查询订单数据，后台模块调用
     *
     * @param pageParam 页面参数
     * @return {@link Long}
     */
    @PostMapping("/order/admin/list")
    R adminPageOrder(@RequestBody PageParam pageParam);
}
