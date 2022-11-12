package com.yecheng.user.controller;

import com.yecheng.param.AddressListParam;
import com.yecheng.param.AddressRemoveParam;
import com.yecheng.pojo.Address;
import com.yecheng.user.service.AddressService;
import com.yecheng.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yelf
 * @create 2022-11-12-15:53
 */
@RestController
@RequestMapping("/user/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 地址查看接口
     *
     * @param addressListParam 地址列表参数user_id
     * @param result           结果
     * @return {@link R}
     */
    @PostMapping("/list")
    public R list(@RequestBody @Validated AddressListParam addressListParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常，查询失败！");
        }
        return addressService.getListById(addressListParam.getUserId());
    }

    /**
     * 保存添加地址
     *
     * @param address 地址
     * @param result  结果
     * @return {@link R}
     */
    @PostMapping("/save")
    public R save(@RequestBody @Validated Address address, BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常，添加失败！");
        }
        return addressService.saveByBean(address);
    }

    /**
     * 删除地址根据id
     *
     * @param removeParam 删除参数id
     * @param result      结果
     * @return {@link R}
     */
    @PostMapping("/remove")
    public R remove(@RequestBody @Validated AddressRemoveParam removeParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常，删除失败！");
        }
        return addressService.deleteById(removeParam.getId());
    }

}
