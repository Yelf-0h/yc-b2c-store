package com.yecheng.admin;

import com.yecheng.clients.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Yelf
 * @create 2022-11-23-23:53
 */
@SpringBootApplication
@MapperScan("com.yecheng.admin.mapper")
@EnableCaching
@EnableFeignClients(clients = {UserClient.class,
        CategoryClient.class,SearchClient.class,
        ProductClient.class, OrderClient.class})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}
