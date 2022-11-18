package com.yecheng.pojo;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Cart)表实体类
 *
 * @author makejava
 * @since 2022-11-18 15:56:42
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cart")
public class Cart implements Serializable {
    public static final Long serialVersionUID = 1L;
    @TableId
    private Integer id;

    
    private Integer userId;
    
    private Integer productId;
    
    private Integer num;



}
