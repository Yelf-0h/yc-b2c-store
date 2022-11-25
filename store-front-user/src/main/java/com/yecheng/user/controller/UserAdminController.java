package com.yecheng.user.controller;

import com.yecheng.param.CartListParam;
import com.yecheng.param.PageParam;
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
 * @create 2022-11-24-14:25
 */
@RestController
@RequestMapping("/user")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/admin/list")
    public R pageUser(@RequestBody PageParam pageParam){

        return userService.pageUser(pageParam);
    }

    /**
     * 根据用户id删除用户,由于CartListParam里刚好只有userid所以复用
     *
     * @param cartListParam 购物车列表参数
     * @return {@link R}
     */
    @PostMapping("/admin/remove")
    public R removeUser(@RequestBody @Validated CartListParam cartListParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常，删除失败！");
        }
        return userService.removeUser(cartListParam.getUserId());

    }

    @PostMapping("/admin/update")
    public R updateUser(@RequestBody @Validated User user,BindingResult result){
        if (result.hasErrors()) {
            return R.fail("参数异常，修改失败！");
        }
        return userService.updateUser(user);
    }

    @PostMapping("/admin/save")
    public R saveUser(@RequestBody @Validated User user,BindingResult result){
        if (result.hasErrors()) {
            return R.fail("参数异常，修改失败！");
        }
        return userService.saveUser(user);
    }

}
