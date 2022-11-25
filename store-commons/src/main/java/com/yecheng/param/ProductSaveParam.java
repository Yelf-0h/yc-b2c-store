package com.yecheng.param;

import com.yecheng.pojo.Product;
import lombok.Data;

/**
 * @author Yelf
 * @create 2022-11-25-1:21
 */
@Data
public class ProductSaveParam extends Product {
    /**
     * 商品详情图片地址, 多图片地址使用 + 号拼接
     */
    private String pictures;
}
