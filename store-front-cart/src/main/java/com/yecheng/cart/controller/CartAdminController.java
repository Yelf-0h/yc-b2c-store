package com.yecheng.cart.controller;

import com.yecheng.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yelf
 * @create 2022-11-25-16:23
 * 被后台模块调用的controller
 */
@RestController
@RequestMapping("/cart")
public class CartAdminController {

    @Autowired
    private CartService cartService;
    @PostMapping("/check")
    public Long checkCart(@RequestBody Integer productId){
        return cartService.checkCart(productId);
    }

}
