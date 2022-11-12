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
 *
 * (Carousel)表实体类
 *
 * @author Yefl
 * @date 2022/11/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("carousel")
public class Carousel implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @JsonProperty("carousel_id")
    private Integer carouselId;

    
    private String imgPath;
    
    private String describes;

    /**
     * 广告关联的商品图片
     */
    @JsonProperty("product_id")
    private Integer productId;
    /**
     * 优先级
     */
    private Integer priority;



}
