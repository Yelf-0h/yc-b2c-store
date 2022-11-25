package com.yecheng.admin.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (AdminUser)表实体类
 *
 * @author makejava
 * @since 2022-11-24 01:23:13
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("admin_user")
public class AdminUser  {
    @TableId(type = IdType.AUTO)
    //管理员主键    
    private Integer userId;

    //管理员昵称
    private String userName;
    //管理员账号
    private String userAccount;
    //管理员密码
    private String userPassword;
    //管理员手机号
    private String userPhone;
    //账号创建时间
    private Date createTime;
    //账号角色编号,用于后期进行权限扩展,前期先为0
    private Integer userRole;



}
