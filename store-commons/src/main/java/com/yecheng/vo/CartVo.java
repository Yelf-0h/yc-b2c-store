package com.yecheng.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yecheng.pojo.Cart;
import com.yecheng.pojo.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Yelf
 * @create 2022-11-18-16:09
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CartVo implements Serializable {

    private Integer id;  //购物车id
    private Integer productID;  //商品id
    private String  productName; //商品名称
    private String  productImg; //商品显示图片
    private Double price;  //商城价格
    private Integer num;  //商品购买数量
    private Integer maxNum; //商品限购数量
    private Boolean check = false; //是否勾选

    public CartVo(Product product, Cart cart) {
        this.id = cart.getId();
        this.productID = product.getProductId();
        this.productName = product.getProductName();
        this.productImg = product.getProductPicture();
        this.price = product.getProductSellingPrice();
        this.num = cart.getNum();
        this.maxNum = product.getProductNum();
        this.check = false;
    }
}
