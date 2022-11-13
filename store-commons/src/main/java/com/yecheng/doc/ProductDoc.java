package com.yecheng.doc;

import com.yecheng.pojo.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yelf
 * @create 2022-11-13-16:12
 */
@Data
@NoArgsConstructor
public class ProductDoc extends Product {
    /**
     * 商品名称和商品标题和商品描述的综合值
     */
    private String all;

    public ProductDoc(Product product){
        super(product.getProductId(),product.getProductName(),product.getCategoryId(),
                product.getProductTitle(),product.getProductIntro(),product.getProductPicture(),
                product.getProductPrice(),product.getProductSellingPrice(),product.getProductNum(),
                product.getProductSales());
        this.all = product.getProductName()+product.getProductTitle()+product.getProductIntro();
    }

}
