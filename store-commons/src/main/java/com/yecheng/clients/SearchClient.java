package com.yecheng.clients;

import com.yecheng.param.ProductSearchParam;
import com.yecheng.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yelf
 * @create 2022-11-13-17:33
 */
@FeignClient("search-service")
public interface SearchClient {

    /**
     * 搜索产品
     *
     * @param productSearchParam 产品搜索参数
     * @return {@link R}
     */
    @PostMapping("/search/product")
    R searchProduct(@RequestBody ProductSearchParam productSearchParam);
}
