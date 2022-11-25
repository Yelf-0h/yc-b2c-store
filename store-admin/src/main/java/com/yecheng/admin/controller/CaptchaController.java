package com.yecheng.admin.controller;

import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码控制器
 *
 * @author Yefl
 * @date 2022/11/24
 */
@Controller
@RequestMapping
public class CaptchaController {

    @RequestMapping("/captcha")
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //默认四个字符长度的验证码
        //默认放入session key = captcha 的位置
        CaptchaUtil.out(request, response);
    }

}