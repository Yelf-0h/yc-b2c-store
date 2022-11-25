package com.yecheng.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yecheng.pojo.Address;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Yelf
 * @create 2022-11-23-15:49
 */
@Data
public class AddressSaveParam {
    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    private Address add;
}
