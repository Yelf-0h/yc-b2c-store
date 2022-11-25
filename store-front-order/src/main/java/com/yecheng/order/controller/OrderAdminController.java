package com.yecheng.order.controller;

import com.yecheng.order.service.OrdersService;
import com.yecheng.param.PageParam;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yelf
 * @create 2022-11-25-16:33
 */
@RestController
@RequestMapping("/order")
public class OrderAdminController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/check")
    public Long checkOrder(@RequestBody Integer productId){
        return ordersService.checkOrder(productId);
    }

    @PostMapping("/admin/list")
    public R pageOrder(@RequestBody PageParam pageParam){
        return ordersService.pageOrder(pageParam);
    }

}
