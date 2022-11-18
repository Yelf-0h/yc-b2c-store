package com.yecheng.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.param.CartListParam;
import com.yecheng.param.CartSaveParam;
import com.yecheng.pojo.Cart;
import com.yecheng.utils.R;

/**
 * (Cart)表服务接口
 *
 * @author makejava
 * @since 2022-11-18 15:56:42
 */
public interface CartService extends IService<Cart> {

    /**
     * 保存商品到购物车
     *
     * @param cartSaveParam 购物车保存参数
     * @return {@link R}
     */
    R saveCart(CartSaveParam cartSaveParam);

    /**
     * 购物车信息列表
     *
     * @param cartListParam 购物车列表参数
     * @return {@link R}
     */
    R listCart(CartListParam cartListParam);
}
