package com.yecheng.user.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.constants.UserConstants;
import com.yecheng.param.PageParam;
import com.yecheng.param.UserCheckParam;
import com.yecheng.param.UserLoginParam;
import com.yecheng.pojo.User;
import com.yecheng.user.mapper.UserMapper;
import com.yecheng.user.service.UserService;
import com.yecheng.utils.MD5Util;
import com.yecheng.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Yelf
 * @create 2022-11-12-2:41
 */
@Service("userService")
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 检查账号是否可用
     *
     * @param userCheckParam 用户检查参数
     * @return {@link R}
     */
    @Override
    public R check(UserCheckParam userCheckParam) {
        /* 1.参数封装,数据库查询 */
        Long count = lambdaQuery().eq(User::getUserName, userCheckParam.getUserName()).count();
        /* 3.查询结果出来 */
        if (count == 0) {
            /* 数据库中不存在这个用户 */
            log.info("UserServiceImpl.check业务结束，结果为：{}", "账号可以使用！");
            return R.ok("账号不存在，可以使用！");
        }
        log.info("UserServiceImpl.check业务结束，结果为：{}", "账号不可使用！");
        return R.fail("账号已经存在，不可注册！");
    }

    /**
     * 用户注册
     * 2.检查账户是否存在
     * 1.密码加密
     * 3.插入数据库
     * 4.返回结果
     *
     * @param user 参数已经校验但是密码为明文
     * @return {@link R} 返回结果 001 004
     */
    @Override
    public R register(User user) {

        /* 1.检查账户是否存在 */
        Long count = lambdaQuery().eq(User::getUserName, user.getUserName()).count();
        if (count > 0) {
            /* 数据库中存在这个用户 */
            log.info("UserServiceImpl.register业务结束，结果为：{}", "");
            return R.fail("账号已经存在，不可注册！");
        }
        /* 2.密码md5加密,加盐 */
        String encode = MD5Util.encode(user.getPassword() + UserConstants.USER_SALT);
        user.setPassword(encode);
        boolean save = save(user);
        if (!save) {
            log.info("UserServiceImpl.register业务结束，结果为：{}", "数据插入失败，注册失败！");
            return R.fail("注册失败！请稍后再试！");
        }
        log.info("UserServiceImpl.register业务结束，结果为：{}", "注册成功！");
        return R.ok("注册成功！");
    }

    /**
     * 用户登录
     * 1.密码加密加盐处理
     * 2.账号密码进行数据库查询，返回一个user对象
     * 3.判断user是否查到
     *
     * @param loginParam 登录参数 账号和密码，已经校验但是密码还是明文
     * @return {@link R} 返回结果 001 004
     */
    @Override
    public R login(UserLoginParam loginParam) {
        /* 1.密码加密加盐处理 */
        String encode = MD5Util.encode(loginParam.getPassword() + UserConstants.USER_SALT);
        /* 2.账号密码进行数据库查询，返回一个user对象 */
        User user = lambdaQuery().eq(User::getUserName, loginParam.getUserName())
                .eq(User::getPassword, encode).one();
        /* 3.判断user是否查到 */
        if (Objects.isNull(user)) {
            log.info("UserServiceImpl.login业务结束，结果为：{}", "账号或者密码错误！");
            return R.fail("账号或者密码错误！");
        }
        log.info("UserServiceImpl.login业务结束，结果为：{}", "登录成功！");
        user.setUserPhonenumber(null);
        user.setPassword(null);
        return R.ok("登录成功", user);
    }

    /**
     * 后台管理调用，查询全部用户数据 页面
     *
     * @param pageParam 页面参数
     * @return {@link R}
     */
    @Override
    public R pageUser(PageParam pageParam) {
        Page<User> userPage = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        page(userPage);
        R ok = R.ok("用户管理查询成功！", userPage.getRecords(), userPage.getTotal());
        return ok;
    }

    /**
     * 根据用户id删除用户
     *
     * @param userId 用户id
     * @return {@link R}
     */
    @Override
    public R removeUser(String userId) {
        boolean b = removeById(userId);
        log.info("UserServiceImpl.removeUser业务结束，结果为：{}", b);
        return R.ok("用户删除成功！");
    }

    /**
     * 更新用户数据
     * id和账号不可修改
     *
     * @param user 用户
     * @return {@link R}
     */
    @Override
    public R updateUser(User user) {
        User byId = getById(user.getUserId());
        if (byId == null) {
            return R.fail("参数异常，修改失败！");
        }
        if (!byId.getPassword().equals(user.getPassword())) {
            byId.setPassword(MD5Util.encode(user.getPassword() + UserConstants.USER_SALT));
        }
        byId.setUserPhonenumber(user.getUserPhonenumber());
        boolean b = updateById(byId);
        if (!b){
            return R.fail("修改用户信息失败！");
        }
        return R.ok("修改用户信息成功！");
    }

    /**
     * 添加用户
     *
     * @param user 用户
     * @return {@link R}
     */
    @Override
    public R saveUser(User user) {
        /* 1.检查账户是否存在 */
        Long count = lambdaQuery().eq(User::getUserName, user.getUserName()).count();
        if (count > 0) {
            /* 数据库中存在这个用户 */
            log.info("UserServiceImpl.saveUser业务结束，结果为：{}","账号已经存在，不可添加！");
            return R.fail("账号已经存在，不可添加！");
        }
        /* 2.密码md5加密,加盐 */
        String encode = MD5Util.encode(user.getPassword() + UserConstants.USER_SALT);
        user.setPassword(encode);
        boolean save = save(user);
        if (!save) {
            log.info("UserServiceImpl.saveUser业务结束，结果为：{}", "数据插入失败，添加用户失败！");
            return R.fail("添加用户失败！请稍后再试！");
        }
        log.info("UserServiceImpl.saveUser业务结束，结果为：{}", "添加用户成功！");
        return R.ok("添加用户成功！");
    }
}
