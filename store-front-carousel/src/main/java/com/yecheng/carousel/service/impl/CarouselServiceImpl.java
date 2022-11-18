package com.yecheng.carousel.service.impl;

import com.yecheng.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.carousel.mapper.CarouselMapper;
import com.yecheng.pojo.Carousel;
import com.yecheng.carousel.service.CarouselService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (Carousel)表服务实现类
 *
 * @author makejava
 * @since 2022-11-12 21:45:40
 */
@Service("carouselService")
@Slf4j
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

    /**
     * 查询轮播图数据列表，只要优先级最高的6条
     *      按照优先级查询数据库数据
     *      我们使用stream流，进行内存数据切割，保留6条数据
     * @return {@link R}
     */
    @Override
    @Cacheable(value = "list.carousel",key = "#root.methodName",cacheManager = "cacheManagerDay")
    public R carouselList() {
        /* 按照优先级查询数据库数据 */
        List<Carousel> carouselList = lambdaQuery().orderByDesc(Carousel::getPriority).list();
        /* 使用stream流，进行内存数据切割，保留6条数据 */
        List<Carousel> collect = carouselList.stream().limit(6).collect(Collectors.toList());
        R ok = R.ok(collect);
        log.info("CarouselServiceImpl.carouselList业务结束，结果为：{}",ok);
        return ok;
    }
}
