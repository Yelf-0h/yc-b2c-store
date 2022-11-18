package com.yecheng.collect.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.yecheng.clients.ProductClient;
import com.yecheng.param.ProductCollectParam;
import com.yecheng.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.collect.mapper.CollectMapper;
import com.yecheng.pojo.Collect;
import com.yecheng.collect.service.CollectService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (Collect)表服务实现类
 *
 * @author makejava
 * @since 2022-11-17 15:06:42
 */
@Service("collectService")
@Slf4j
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private ProductClient productClient;
    /**
     * 收藏保存服务
     *
     * @param collect 收集
     * @return
     */
    @Override
    public R savaCollect(Collect collect) {
        Long count = lambdaQuery().eq(Collect::getUserId, collect.getUserId())
                .eq(Collect::getProductId, collect.getProductId())
                .count();
        if (count > 0){
            return R.fail("该商品已经添加收藏，请到我的收藏查看");
        }
        collect.setCollectTime(System.currentTimeMillis());
        boolean save = save(collect);
        if (!save){
            return R.fail("商品添加出错！");
        }
        log.info("CollectServiceImpl.savaCollect业务结束，结果为：{}",save);
        return R.ok("添加收藏成功");
    }

    /**
     * 根据用户id查询商品信息集合
     *
     * @param userId
     * @return {@link R}
     */
    @Override
    public R listCollect(Integer userId) {
        List<Integer> ids = lambdaQuery().eq(Collect::getUserId, userId).list()
                .stream().map(Collect::getProductId).collect(Collectors.toList());
        ProductCollectParam productCollectParam = new ProductCollectParam();
        productCollectParam.setProductIds(ids);
        R r = productClient.productIds(productCollectParam);
        log.info("CollectServiceImpl.listCollect业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 根据用户id和商品id删除收藏
     *
     * @param collect 收集
     * @return {@link R}
     */
    @Override
    public R removeCollect(Collect collect) {
        LambdaQueryChainWrapper<Collect> eq = lambdaQuery().eq(Collect::getUserId, collect.getUserId())
                .eq(Collect::getProductId, collect.getProductId());
        boolean remove = remove(eq);
        if (!remove){
            return R.fail("取消收藏失败！");
        }
        log.info("CollectServiceImpl.removeCollect业务结束，结果为：{}","收藏移除成功");
        return R.ok("收藏移除成功！");
    }
}
