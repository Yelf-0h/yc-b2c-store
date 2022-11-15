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
 * (Picture)表实体类
 *
 * @author makejava
 * @since 2022-11-13 03:11:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("product_picture")
public class Picture implements Serializable {
    public static final Long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("product_picture")
    private String productPicture;
    
    private String intro;



}
