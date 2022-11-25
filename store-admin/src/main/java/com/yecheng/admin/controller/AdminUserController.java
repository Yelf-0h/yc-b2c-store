package com.yecheng.admin.controller;

import com.yecheng.admin.param.AdminUserParam;
import com.yecheng.admin.pojo.AdminUser;
import com.yecheng.admin.service.AdminUserService;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Yelf
 * @create 2022-11-24-1:29
 * /admin/user/login。。。。的controller，但是/admin提取到配置文件中
 */
@RestController
@RequestMapping
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/user/login")
    public R login(@Validated AdminUserParam adminUserParam, BindingResult result,
                   HttpSession session){
        if (result.hasErrors()){
            return R.fail("账号参数有误，登录失败！");
        }
        /* 验证码校验，通过captcha生成的验证码在session中key为captcha的域中 */
        String captcha = (String) session.getAttribute("captcha");

        if (!adminUserParam.getVerCode().equalsIgnoreCase(captcha)){
            return R.fail("验证码错误！");
        }
        AdminUser adminUser = adminUserService.login(adminUserParam);
        if (adminUser == null){
            return R.fail("登录失败！账号或密码错误！");
        }

        session.setAttribute("userInfo",adminUser);

        return R.ok("登录成功！");
    }

    @GetMapping("/user/logout")
    public R logout(HttpSession session){
        /* 清空session数据 */
        session.invalidate();

        return R.ok("退出登录成功！");
    }


}
