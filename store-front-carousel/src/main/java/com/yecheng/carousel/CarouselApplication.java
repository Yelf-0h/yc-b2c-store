package com.yecheng.carousel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Yelf
 * @create 2022-11-12-21:41
 */
@SpringBootApplication
@MapperScan("com.yecheng.carousel.mapper")
public class CarouselApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarouselApplication.class,args);
    }
}
