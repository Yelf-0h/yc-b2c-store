package com.yecheng.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

/**
 * 购物车保存参数
 *
 * @author Yelf
 * @create 2022-11-18-16:15
 * @date 2022/11/18
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartSaveParam {

    @JsonProperty("user_id")
    @NotNull
    private Integer userId;
    @JsonProperty("product_id")
    @NotNull
    private Integer productId;
    private Integer num;
}

