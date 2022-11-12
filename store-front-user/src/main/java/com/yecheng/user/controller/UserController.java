package com.yecheng.user.controller;

import com.yecheng.param.UserCheckParam;
import com.yecheng.param.UserLoginParam;
import com.yecheng.pojo.User;
import com.yecheng.user.service.UserService;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yelf
 * @create 2022-11-12-2:25
 * 用户模块控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 检查
     *
     * @param userCheckParam 接受检查账号实体 内部有参数校验注解
     * @param result         获取参数检验结果
     * @return {@link R} 返回封装结果R对象
     */
    @PostMapping("/check")
    public R check(@RequestBody @Validated UserCheckParam userCheckParam, BindingResult result){
        boolean hasErrors = result.hasErrors();
        if (hasErrors){
            return R.fail("账号为null，不可使用！");
        }
        return userService.check(userCheckParam);
    }

    /**
     * 用户注册
     *
     * @param user   用户实体
     * @param result 获取参数检验结果
     * @return {@link R}
     */
    @PostMapping("/register")
    public R register(@RequestBody @Validated User user, BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常，不可注册！");
        }
        return userService.register(user);
    }

    /**
     * 用户登录
     *
     * @param loginParam 登录参数
     * @param result     结果
     * @return {@link R}
     */
    @PostMapping("/login")
    public R login(@RequestBody @Validated UserLoginParam loginParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常，登录失败！");
        }
        return userService.login(loginParam);
    }




}
