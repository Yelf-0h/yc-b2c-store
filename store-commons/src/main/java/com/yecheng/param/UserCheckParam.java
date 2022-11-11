package com.yecheng.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Yelf
 * @create 2022-11-12-2:20
 * 接受前端参数的param
 */
@Data
public class UserCheckParam {
    /**
     * 用户名
     * 用@NotBlank能指定这个userName不能为空
     */
    @NotBlank
    private String userName;
}
