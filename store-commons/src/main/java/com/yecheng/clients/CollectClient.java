package com.yecheng.clients;

import com.yecheng.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yelf
 * @create 2022-11-25-17:14
 */
@FeignClient("collect-service")
public interface CollectClient {

    /**
     * 删除收藏根据pid，，后台管理模块删除商品服务调用
     *
     * @param productId 产品id
     * @return {@link R}
     */
    @PostMapping("/collect/remove/bypid")
    R adminRemoveCollect(@RequestBody Integer productId);
}
