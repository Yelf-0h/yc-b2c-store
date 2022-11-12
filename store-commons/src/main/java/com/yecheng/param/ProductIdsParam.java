package com.yecheng.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Yelf
 * @create 2022-11-13-2:40
 */
@Data
public class ProductIdsParam {

    @NotNull
    private List<Integer> categoryID;
    private Integer currentPage = 1;
    private Integer pageSize = 15;
}
