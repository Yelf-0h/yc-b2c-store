package com.yecheng.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Yelf
 * @create 2022-11-12-16:21
 */
@Data
public class AddressRemoveParam {
    @NotNull
    private Integer id;
}
