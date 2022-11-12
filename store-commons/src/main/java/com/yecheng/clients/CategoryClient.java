package com.yecheng.clients;

import com.yecheng.param.ProductHotsParam;
import com.yecheng.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yelf
 * @create 2022-11-13-0:01
 * 类别的调用接口
 */
@FeignClient("category-service")
public interface CategoryClient {

    /**
     * 根据类别名称查询类别对象
     *
     * @param categoryName 类别名称
     * @return {@link R}
     */
    @GetMapping("/category/promo/{categoryName}")
    R byName(@PathVariable String categoryName);

    /**
     * 根据类别名称集合查询类别id集合
     *
     * @param productHotsParam 产品还有参数
     * @return {@link R}
     */
    @PostMapping("/category/hots")
    R hotsCategory(@RequestBody ProductHotsParam productHotsParam);

    /**
     * 查询类别列表进行返回
     *
     * @return {@link R}
     */
    @GetMapping("/category/list")
    R listCategory();
}
