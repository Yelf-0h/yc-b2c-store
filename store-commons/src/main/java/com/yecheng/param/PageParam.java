package com.yecheng.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yelf
 * @create 2022-11-13-16:42
 */
@Data
public class PageParam {
    private Integer currentPage = 1;
    private Integer pageSize = 15;

}
