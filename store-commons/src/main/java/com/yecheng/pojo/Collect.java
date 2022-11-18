package com.yecheng.pojo;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Collect)表实体类
 *
 * @author makejava
 * @since 2022-11-17 15:06:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("collect")
public class Collect implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("collect_time")
    private Long collectTime;



}
