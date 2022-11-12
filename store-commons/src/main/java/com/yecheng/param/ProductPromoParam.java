package com.yecheng.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Yelf
 * @create 2022-11-12-23:39
 */
@Data
public class ProductPromoParam {

    @NotBlank
    private String categoryName;
}
