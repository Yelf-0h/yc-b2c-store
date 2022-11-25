package com.yecheng.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * admin后台管理模块里订单的vo实体
 *
 * @author Yefl
 * @date 2022/11/25
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AdminOrderVo {

    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("user_id")
    private Integer userId;
    /**
     * 商品种类
     */
    @JsonProperty("product_num")
    private Integer productNum;
    /**
     * 订单中商品数量
     */
    @JsonProperty("order_num")
    private Integer orderNum;
    /**
     * 订单金额
     */
    @JsonProperty("order_price")
    private Double orderPrice;
    /**
     * 订单时间
     */
    @JsonProperty("order_time")
    private Long orderTime;
}