package com.yecheng.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Yelf
 * @create 2022-11-13-1:41
 */
@Data
public class ProductHotsParam {
    @NotEmpty
    private List<String> categoryName;
}
