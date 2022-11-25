package com.yecheng.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.param.OrderParam;
import com.yecheng.param.PageParam;
import com.yecheng.pojo.Orders;
import com.yecheng.utils.R;

/**
 * (Orders)表服务接口
 *
 * @author makejava
 * @since 2022-11-22 10:43:03
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 保存订单
     *
     * @param orderParam 命令参数
     * @return {@link R}
     */
    R saveOrder(OrderParam orderParam);

    /**
     * 分组查询订单数据列表
     *
     * @param userId 用户id
     * @return {@link R}
     */
    R listOrder(String userId);

    /**
     * 检查订单中是否有使用到该商品的订单
     *
     * @param productId 产品id
     * @return {@link Long}
     */
    Long checkOrder(Integer productId);

    /**
     * 分页查询订单数据
     *
     * @param pageParam 页面参数
     * @return {@link R}
     */
    R pageOrder(PageParam pageParam);
}
