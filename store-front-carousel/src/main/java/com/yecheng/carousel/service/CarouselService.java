package com.yecheng.carousel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.pojo.Carousel;
import com.yecheng.utils.R;

/**
 * (Carousel)表服务接口
 *
 * @author makejava
 * @since 2022-11-12 21:45:38
 */
public interface CarouselService extends IService<Carousel> {

    /**
     *  查询轮播图数据列表，只要优先级最高的6条
     * @return {@link R}
     */
    R carouselList();
}
