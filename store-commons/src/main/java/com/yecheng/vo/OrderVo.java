package com.yecheng.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yecheng.pojo.Orders;
import lombok.Data;

/**
 * @author Yelf
 * @create 2022-11-23-15:03
 * 订单返回的数据实体
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderVo extends Orders {

    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_picture")
    private String productPicture;

}
