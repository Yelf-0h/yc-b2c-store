package com.yecheng.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Yelf
 * @create 2022-11-13-3:02
 */
@Data
public class ProductIdParam {
    @NotNull
    private Integer productID;
}
