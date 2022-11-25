package com.yecheng.product.listener;

import com.yecheng.param.OrderToProductParam;
import com.yecheng.product.service.ProductService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-11-22-23:12
 */
@Component
public class ProductRabbitMqListener {

    @Autowired
    private ProductService productService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "sub.queue"),
                    exchange = @Exchange(value = "topic.ex"),
                    key = "sub.number"
            )
    )
    public void subNumber(List<OrderToProductParam> orderToProductParamList){
        productService.subNumber(orderToProductParamList);
    }
}
