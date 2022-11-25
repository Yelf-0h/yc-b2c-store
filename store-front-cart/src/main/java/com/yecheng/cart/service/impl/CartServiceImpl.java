package com.yecheng.cart.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.yecheng.clients.ProductClient;
import com.yecheng.param.CartListParam;
import com.yecheng.param.CartSaveParam;
import com.yecheng.param.ProductCollectParam;
import com.yecheng.param.ProductIdParam;
import com.yecheng.pojo.Product;
import com.yecheng.utils.R;
import com.yecheng.vo.CartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.cart.mapper.CartMapper;
import com.yecheng.pojo.Cart;
import com.yecheng.cart.service.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (Cart)表服务实现类
 *
 * @author makejava
 * @since 2022-11-18 15:56:42
 */
@Service("cartService")
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private ProductClient productClient;
    /**
     * 保存商品到购物车
     *
     * @param cartSaveParam 购物车保存参数
     * @return {@link R}
     */
    @Override
    public R saveCart(CartSaveParam cartSaveParam) {
        /*查询商品数据*/
        ProductIdParam idParam = new ProductIdParam();
        idParam.setProductID(cartSaveParam.getProductId());
        Product product = productClient.cartDetail(idParam);
        if (product == null) {
            return R.fail("商品已经不存在，无法添加到购物车！");
        }
        /*检查库存*/
        if (product.getProductNum() <= 0) {
            R ok = R.ok("没有库存了！无法购买");
            ok.setCode("003");
            log.info("CartServiceImpl.saveCart业务结束，结果为：{}",ok);
            return ok;
        }
        /*查询购物车中是否存在*/
        Cart cart = lambdaQuery().eq(Cart::getUserId, cartSaveParam.getUserId())
                .eq(Cart::getProductId, cartSaveParam.getProductId()).one();
        if (cart != null){
            /* 证明购物车存在此商品 所以原有数量+1即可 */
            cart.setNum(cart.getNum()+1);
            updateById(cart);
            /* 返回002提示*/
            R ok = R.ok("购物车已存在该商品，数量+1");
            ok.setCode("002");
            log.info("CartServiceImpl.saveCart业务结束，结果为：{}",ok);
            return ok;
        }
        cart = new Cart();
        cart.setUserId(cartSaveParam.getUserId());
        cart.setProductId(cartSaveParam.getProductId());
        cart.setNum(1);
        boolean save = save(cart);
        /*封装返回结果vo*/
        log.info("CartServiceImpl.saveCart业务结束，结果为：{}",save);
        CartVo cartVo = new CartVo(product,cart);
        R ok = R.ok("购物车数据添加成功！", cartVo);
        log.info("CartServiceImpl.saveCart业务结束，结果为：{}",ok);
        return ok;
    }

    /**
     * 购物车信息列表
     *
     * @param cartListParam 购物车列表参数
     * @return {@link R} 确保返回集合
     */
    @Override
    public R listCart(CartListParam cartListParam) {
        /*根据用户id查询购物车列表*/
        List<Cart> carts = lambdaQuery().eq(Cart::getUserId, cartListParam.getUserId()).list();
        List<CartVo> cartVos = new ArrayList<>();
        if (carts == null ||carts.size() ==0){
            return R.ok("购物车为空！",cartVos);
        }
        List<Integer> ids = carts.stream().map(Cart::getProductId).collect(Collectors.toList());
        ProductCollectParam collectParam = new ProductCollectParam();
        collectParam.setProductIds(ids);
        List<Product> productList = productClient.cartList(collectParam);
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));
        for (Cart cart : carts) {
            CartVo cartVo = new CartVo(productMap.get(cart.getProductId()),cart);
            cartVos.add(cartVo);
        }
        R ok = R.ok("数据库数据查询成功！", cartVos);
        log.info("CartServiceImpl.listCart业务结束，结果为：{}",ok);
        return ok;
    }

    /**
     * 更新购物车
     *
     * @param cartSaveParam 购物车保存参数
     * @return
     */
    @Override
    public R updateCart(CartSaveParam cartSaveParam) {
        ProductIdParam idParam = new ProductIdParam();
        idParam.setProductID(cartSaveParam.getProductId());
        Product product = productClient.cartDetail(idParam);
        /*判断库存是否足够*/
        if (cartSaveParam.getNum()>product.getProductNum()){
            log.info("CartServiceImpl.updateCart业务结束，结果为：{}","库存不足，修改失败！");
            return R.fail("库存不足，修改失败！");
        }
        /*修改数据*/
        Cart cart = lambdaQuery().eq(Cart::getUserId, cartSaveParam.getUserId())
                .eq(Cart::getProductId, cartSaveParam.getProductId()).one();
        cart.setNum(cartSaveParam.getNum());
        boolean b = updateById(cart);
        log.info("CartServiceImpl.updateCart业务结束，结果为：{}",b);
        return R.ok("修改数量成功！");
    }

    /**
     * 删除购物车
     *
     * @param cartSaveParam 购物车保存参数
     * @return {@link R}
     */
    @Override
    public R removeCart(CartSaveParam cartSaveParam) {
        LambdaQueryChainWrapper<Cart> eq = lambdaQuery().eq(Cart::getUserId, cartSaveParam.getUserId())
                .eq(Cart::getProductId, cartSaveParam.getProductId());
        boolean remove = remove(eq);
        if (!remove) {
            return R.fail("删除失败！");
        }
        log.info("CartServiceImpl.removeCart业务结束，结果为：{}","成功删除此购物车项！");
        return R.ok("成功删除此购物车项！");
    }

    /**
     * 清空对应id的购物车项
     *
     * @param cartIds 购物车id
     */
    @Override
    public void clearIds(List<Integer> cartIds) {
        removeBatchByIds(cartIds);
        log.info("CartServiceImpl.clearIds业务结束，结果为：{} 以上id删除",cartIds);
    }

    /**
     * 检查购物车是否有正在使用该商品的
     *
     * @param productId 产品id
     * @return {@link Long}
     */
    @Override
    public Long checkCart(Integer productId) {
        Long count = lambdaQuery().eq(Cart::getProductId, productId).count();
        log.info("CartServiceImpl.checkCart业务结束，结果为：{}",count);
        return count;
    }
}
