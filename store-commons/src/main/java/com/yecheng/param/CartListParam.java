package com.yecheng.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Yelf
 * @create 2022-11-18-23:06
 */
@Data
public class CartListParam {
    @JsonProperty("user_id")
    @NotNull
    private String userId;
}
