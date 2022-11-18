package com.yecheng.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Yelf
 * @create 2022-11-17-16:22
 */
@Data
public class ProductCollectParam {
    @NotEmpty
    private List<Integer> productIds;
}
