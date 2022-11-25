package com.yecheng.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yecheng.vo.CartVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 命令参数
 *
 * @author Yefl
 * @date 2022/11/22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrderParam implements Serializable {

    public static final Long serialVersionUID = 1L;

    @JsonProperty("user_id")
    private Integer userId;
    private List<CartVo> products;

}