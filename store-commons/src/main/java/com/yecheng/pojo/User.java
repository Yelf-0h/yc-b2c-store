package com.yecheng.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Yelf
 * @create 2022-11-12-2:00
 * 用户实体类
 */
@Data
@TableName("user")
public class User implements Serializable {

    public static final Long serialVersionUID = 1L;

    /**
     * jackson的注解,用于进行属性格式化!
     * 以后接收或者返回都只能用user_id
     */
    @JsonProperty("user_id")
    @TableId(type =  IdType.AUTO)
    private Integer userId;

    @Length(min = 6)
    private String userName;
    /**
     * 1.忽略属性 不生成json 不接受json数据  @JsonIgnore
     * 2.@JsonInclude(JsonInclude.Include.NON_NULL)  + null 当这个值不为null的时候生成json,为null不生成
     * 3.不影响接收json
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String userPhonenumber;
}

