package com.yecheng.carousel.controller;

import com.yecheng.carousel.service.CarouselService;
import com.yecheng.pojo.Carousel;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 轮播图的控制类
 * @author Yelf
 * @create 2022-11-12-21:53
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;
    /**
     * 查询轮播图数据列表，只要优先级最高的6条
     * @return {@link R}
     */
    @PostMapping("/list")
    public R list(){
        return carouselService.carouselList();
    }
}
