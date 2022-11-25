package com.yecheng.param;

import lombok.Data;

/**
 * 以产品参数
 *
 * @author Yefl
 * @date 2022/11/22
 */
@Data
public class OrderToProductParam {

    /**
     * 产品id
     */
    private Integer productId;
    /**
     * 购买数量
     */
    private Integer num;
}