package com.yecheng.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.pojo.Address;
import com.yecheng.user.mapper.AddressMapper;
import com.yecheng.user.service.AddressService;
import com.yecheng.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-11-12-16:00
 */
@Service
@Slf4j
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
    /**
     * 根据用户id查询地址列表
     *
     * @param userId 用户id，已经校验完毕
     * @return {@link R} 001 004
     */
    @Override
    public R getListById(Integer userId) {
        /* 1.根据userId这条件查询数据库列表 */
        List<Address> addressList = lambdaQuery().eq(Address::getUserId, userId).list();


        /* 2.结果封装 */
        R ok = R.ok("查询成功", addressList);
        log.info("AddressServiceImpl.list业务结束，结果为：{}",ok);
        return ok;
    }

    /**
     * 根据bean添加保存，插入成功后返回新的地址集合
     *
     * @param address 地址实体类，已校验完毕
     * @return {@link R} 数据集合
     */
    @Override
    public R saveByBean(Address address) {
        /* 根据bean添加保存 */
        boolean save = save(address);
        /* 插入成功后返回新的地址集合 */
        if (!save){
            log.info("AddressServiceImpl.saveByBean业务结束，结果为：{}","插入地址失败");
            return R.fail("插入地址失败！");
        }
        R ok = getListById(address.getUserId());
        return ok;
    }

    /**
     * 删除地址数据通过id
     *
     * @param id 地址id
     * @return {@link R} 001 004
     */
    @Override
    public R deleteById(Integer id) {
        boolean remove = removeById(id);
        if (!remove){
            log.info("AddressServiceImpl.deleteById业务结束，结果为：{}","地址删除失败！");
            return R.fail("删除地址数据失败！");
        }
        log.info("AddressServiceImpl.deleteById业务结束，结果为：{}","地址删除成功！");
        return R.ok("地址删除成功！");
    }
}
