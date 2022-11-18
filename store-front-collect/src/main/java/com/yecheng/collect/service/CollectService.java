package com.yecheng.collect.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.pojo.Collect;
import com.yecheng.utils.R;

/**
 * (Collect)表服务接口
 *
 * @author makejava
 * @since 2022-11-17 15:06:42
 */
public interface CollectService extends IService<Collect> {

    /**
     *
     * 收藏保存服务
     *
     * @param collect 收集
     * @return {@link R}
     */
    R savaCollect(Collect collect);

    /**
     * 根据用户id查询商品信息集合
     *
     * @param userId
     * @return {@link R}
     */
    R listCollect(Integer userId);

    /**
     * 根据用户id和商品id删除收藏
     *
     * @param collect 收集
     * @return {@link R}
     */
    R removeCollect(Collect collect);
}
