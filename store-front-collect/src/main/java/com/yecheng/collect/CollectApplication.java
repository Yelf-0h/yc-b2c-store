package com.yecheng.collect;

import com.yecheng.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Yelf
 * @create 2022-11-17-15:14
 */
@SpringBootApplication
@MapperScan("com.yecheng.collect.mapper")
@EnableFeignClients(clients = {ProductClient.class})
public class CollectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollectApplication.class,args);
    }
}
