package com.yecheng.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Yelf
 * @create 2022-11-12-15:51
 */
@Data
public class AddressListParam {
    @JsonProperty("user_id")
    @NotNull
    private Integer userId;
}
