package com.yecheng.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.pojo.Address;
import com.yecheng.utils.R;

/**
 * @author Yelf
 * @create 2022-11-12-15:58
 */
public interface AddressService extends IService<Address> {
    /**
     * 根据用户id查询地址列表
     *
     * @param userId 用户id，已经校验完毕
     * @return {@link R} 001 004
     */
    R getListById(Integer userId);

    /**
     * 根据bean添加保存，插入成功后返回新的地址集合
     *
     * @param address 地址实体类，已校验完毕
     * @return {@link R} 数据集合
     */
    R saveByBean(Address address);

    /**
     * 删除地址数据通过id
     *
     * @param id 地址id
     * @return {@link R} 001 004
     */
    R deleteById(Integer id);
}
