package com.yecheng.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yecheng.pojo.Orders;
import com.yecheng.vo.AdminOrderVo;

/**
 * (Orders)表数据库访问层
 *
 * @author makejava
 * @since 2022-11-22 10:43:03
 */
public interface OrdersMapper extends BaseMapper<Orders> {
    /**
     * 查询分页订单，后台管理模块需要
     *
     * @param page 页面
     * @return {@link Page}<{@link AdminOrderVo}>
     */
    Page<AdminOrderVo> selectAdminOrders(Page<AdminOrderVo> page);
}
