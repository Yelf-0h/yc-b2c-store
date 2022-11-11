package com.yecheng.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Yelf
 * @create 2022-11-12-3:44
 */
@Data
public class UserLoginParam {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
