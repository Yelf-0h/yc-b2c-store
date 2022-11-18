package com.yecheng.collect.controller;

import com.yecheng.collect.service.CollectService;
import com.yecheng.pojo.Collect;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yelf
 * @create 2022-11-17-15:10
 * 收藏Controller
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    @PostMapping("/save")
    public R savaCollect(@RequestBody Collect collect){
        return collectService.savaCollect(collect);
    }
    @PostMapping("/list")
    public R listCollect(@RequestBody Collect collect){
        return collectService.listCollect(collect.getUserId());
    }
    @PostMapping("/remove")
    public R removeCollect(@RequestBody Collect collect){
        return collectService.removeCollect(collect);
    }


}
