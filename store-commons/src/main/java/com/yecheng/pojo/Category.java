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
 * 类别
 * (Category)表实体类
 *
 * @author Yefl
 * @date 2022/11/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("category")
public class Category  {

    @TableId(type = IdType.AUTO)
    @JsonProperty("category_id")
    private Integer categoryId;
    @JsonProperty("category_name")
    private String categoryName;



}
