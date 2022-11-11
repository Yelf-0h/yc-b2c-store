package com.yecheng.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.param.UserCheckParam;
import com.yecheng.param.UserLoginParam;
import com.yecheng.pojo.User;
import com.yecheng.utils.R;

/**
 * @author Yelf
 * @create 2022-11-12-2:40
 */
public interface UserService extends IService<User> {
    /**
     * 检查账号是否可用
     *
     * @param userCheckParam 用户检查参数
     * @return {@link R}
     */
    R check(UserCheckParam userCheckParam);

    /**
     * 用户注册
     *
     * @param user 参数已经校验但是密码为明文
     * @return {@link R} 返回结果 001 004
     */
    R register(User user);

    /**
     * 用户登录
     *
     * @param loginParam 登录参数 账号和密码，已经校验但是密码还是明文
     * @return {@link R} 返回结果 001 004
     */
    R login(UserLoginParam loginParam);
}
