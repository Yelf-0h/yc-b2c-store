package com.yecheng.order.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yecheng.clients.ProductClient;
import com.yecheng.param.OrderParam;
import com.yecheng.param.OrderToProductParam;
import com.yecheng.param.PageParam;
import com.yecheng.param.ProductCollectParam;
import com.yecheng.pojo.Product;
import com.yecheng.utils.R;
import com.yecheng.vo.AdminOrderVo;
import com.yecheng.vo.CartVo;
import com.yecheng.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.order.mapper.OrdersMapper;
import com.yecheng.pojo.Orders;
import com.yecheng.order.service.OrdersService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (Orders)表服务实现类
 *
 * @author makejava
 * @since 2022-11-22 10:43:03
 */
@Service("ordersService")
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 保存订单
     *
     * @param orderParam 命令参数
     * @return {@link R}
     */
    @Override
    @Transactional
    public R saveOrder(OrderParam orderParam) {
        List<Integer> cartIds = new ArrayList<>();
        List<OrderToProductParam> orderToProducts = new ArrayList<>();
        List<Orders> ordersList = new ArrayList<>();

        Integer userId = orderParam.getUserId();
        long orderId = System.currentTimeMillis();

        for (CartVo cartVo : orderParam.getProducts()) {
            /* 保存购物车项的id，方便后面批量删除清空购物车 */
            cartIds.add(cartVo.getId());
            /* 保存商品id和数量 传到商品服务减少库存 */
            OrderToProductParam orderToProductParam = new OrderToProductParam();
            orderToProductParam.setProductId(cartVo.getProductID());
            orderToProductParam.setNum(cartVo.getNum());
            orderToProducts.add(orderToProductParam);

            /* 将每个购物车项的数据转化为订单项 存入订单集合 */
            Orders orders = new Orders();
            orders.setOrderId(orderId);
            orders.setOrderTime(orderId);
            orders.setUserId(userId);
            orders.setProductId(cartVo.getProductID());
            orders.setProductNum(cartVo.getNum());
            orders.setProductPrice(cartVo.getPrice());
            ordersList.add(orders);
        }

        /*订单批量保存*/
        boolean saveBatch = saveBatch(ordersList);
        /* 修改商品库存 [product-service] [异步通知]*/
        /**
         *  交换机: topic.ex
         *  routingkey: sub.number
         *  消息: 商品id和减库存数据集合
         */
        rabbitTemplate.convertAndSend("topic.ex","sub.number",orderToProducts);

        /*清空对应购物车数据即可 [注意: 不是清空用户所有的购物车数据] [cart-service] [异步通知]*/
        /**
         * 交换机:topic.ex
         * routingkey: clear.cart
         * 消息: 要清空的购物车id集合
         */
        rabbitTemplate.convertAndSend("topic.ex","clear.cart",cartIds);

        R ok = R.ok("订单生成成功!");
        log.info("OrderServiceImpl.save业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * 分组查询订单数据列表
     *
     * @param userId 用户id
     * @return {@link R}
     */
    @Override
    public R listOrder(String userId) {
        /* 获取所有是userId的订单列表 */
        List<Orders> ordersList = lambdaQuery().eq(Orders::getUserId, userId).orderByDesc(Orders::getOrderTime).list();
        /* 将订单列表按照订单id进行分组 */
        Map<Long, List<Orders>> orderMap = ordersList.stream().collect(Collectors.groupingBy(Orders::getOrderId));
        /* 将查出来的订单列表过滤出所有的商品id */
        List<Integer> productIds = ordersList.stream().map(Orders::getProductId).collect(Collectors.toList());
        /* 通过商品id的集合查询所有商品详情 */
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productCollectParam);
        /* 装成map */
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));
        /* 结果封装 */
        List<List<OrderVo>> result = new ArrayList<>();

        /* 遍历订单项集合 */
        for (List<Orders> orders : orderMap.values()) {
            List<OrderVo> orderVos = new ArrayList<>();
            for (Orders order : orders) {
                OrderVo orderVo = new OrderVo();
                BeanUtils.copyProperties(order,orderVo);
                Product product = productMap.get(order.getProductId());
                orderVo.setProductName(product.getProductName());
                orderVo.setProductPicture(product.getProductPicture());
                orderVos.add(orderVo);
            }
            result.add(orderVos);
        }

        R ok = R.ok("订单数据获取成功！", result);
        log.info("OrdersServiceImpl.listOrder业务结束，结果为：{}",ok);
        return ok;
    }

    /**
     * 检查订单中是否有使用到该商品的订单
     *
     * @param productId 产品id
     * @return {@link Long}
     */
    @Override
    public Long checkOrder(Integer productId) {
        Long count = lambdaQuery().eq(Orders::getProductId, productId).count();
        log.info("OrdersServiceImpl.checkOrder业务结束，结果为：{}",count);
        return count;
    }

    /**
     * 分页查询订单数据
     *
     * @param pageParam 页面参数
     * @return {@link R}
     */
    @Override
    public R pageOrder(PageParam pageParam) {
        Page<AdminOrderVo> adminOrderVoPage = baseMapper.selectAdminOrders(new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize()));
        List<AdminOrderVo> records = adminOrderVoPage.getRecords();
        long total = adminOrderVoPage.getTotal();
        R r = R.ok("查询订单数据成功！", records, total);
        log.info("OrdersServiceImpl.pageOrder业务结束，结果为：{}",r);
        return r;
    }


}
