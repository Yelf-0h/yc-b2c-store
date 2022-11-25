package com.yecheng.order.controller;

import com.yecheng.order.service.OrdersService;
import com.yecheng.param.CartListParam;
import com.yecheng.param.OrderParam;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yelf
 * @create 2022-11-22-10:50
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/save")
    public R saveOrder(@RequestBody OrderParam orderParam){
        return ordersService.saveOrder(orderParam);
    }

    @PostMapping("/list")
    public R listOrder(@RequestBody @Validated CartListParam cartListParam, BindingResult result){
        if (result.hasErrors()) {
            return R.fail("参数异常，查询失败！");
        }
        return ordersService.listOrder(cartListParam.getUserId());
    }
}
