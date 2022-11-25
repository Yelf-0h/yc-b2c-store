package com.yecheng.collect.controller;

import com.yecheng.collect.service.CollectService;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yelf
 * @create 2022-11-25-17:05
 */
@RestController
@RequestMapping("/collect")
public class CollectAdminController {

    @Autowired
    private CollectService collectService;

    @PostMapping("/remove/bypid")
    public R removeByPID(@RequestBody Integer productId){
        return collectService.removeByPID(productId);
    }
}
