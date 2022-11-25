package com.yecheng.admin.controller;

import com.yecheng.admin.service.AdminUserService;
import com.yecheng.param.CartListParam;
import com.yecheng.param.PageParam;
import com.yecheng.pojo.User;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yelf
 * @create 2022-11-24-14:37
 *
 * /admin开头的controller，但是/admin提取到配置文件中
 * 所以此处/user开头
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping("/list")
    public R listUser(PageParam pageParam){
        return adminUserService.listUser(pageParam);
    }

    @PostMapping("/remove")
    public R removeUser(CartListParam cartListParam){
        return adminUserService.removeUser(cartListParam);
    }

    @PostMapping("/update")
    public R updateUser(User user){
        return adminUserService.updateUser(user);
    }

    @PostMapping("/save")
    public R saveUser(User user){
        return adminUserService.saveUser(user);
    }


}
