package com.yecheng.clients;

import com.yecheng.param.CartListParam;
import com.yecheng.param.PageParam;
import com.yecheng.pojo.User;
import com.yecheng.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Yelf
 * @create 2022-11-24-14:33
 */
@FeignClient("user-service")
public interface UserClient {

    /**
     * 后台管理调用，查询全部用户数据
     *
     * @param pageParam 页面参数
     * @return {@link R}
     */
    @PostMapping("/user/admin/list")
    R adminPageUser(@RequestBody PageParam pageParam);

    /**
     * 根据用户id删除用户
     *
     * @param cartListParam 购物车列表参数
     * @return {@link R}
     */
    @PostMapping("/user/admin/remove")
    R adminRemoveUser(@RequestBody CartListParam cartListParam);

    /**
     * 更新用户
     * 更新用户信息
     *
     * @param user 用户
     * @return {@link R}
     */
    @PostMapping("/user/admin/update")
    R adminUpdateUser(@RequestBody User user);

    /**
     * 添加用户
     *
     * @param user 用户
     * @return {@link R}
     */
    @PostMapping("/user/admin/save")
    R adminSaveUser(@RequestBody User user);

}
