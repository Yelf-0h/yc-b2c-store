package com.yecheng.admin.controller;

import com.yecheng.admin.service.AdminUserService;
import com.yecheng.param.PageParam;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yelf
 * @create 2022-11-25-21:02
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private AdminUserService adminUserService;

    @GetMapping("/list")
    public R pageOrder(PageParam pageParam){
        return adminUserService.pageOrder(pageParam);
    }
}
