package com.yecheng.cart.controller;

import com.yecheng.cart.service.CartService;
import com.yecheng.param.CartListParam;
import com.yecheng.param.CartSaveParam;
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
 * @create 2022-11-18-16:08
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/save")
    public R saveCart(@RequestBody @Validated CartSaveParam cartSaveParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("核心参数为null，添加失败！");
        }
        return cartService.saveCart(cartSaveParam);
    }
    @PostMapping("/list")
    public R listCart(@RequestBody @Validated CartListParam cartListParam,BindingResult result){
        if (result.hasErrors()){
            return R.fail("核心参数为null，查询失败！");
        }
        return cartService.listCart(cartListParam);
    }




}
