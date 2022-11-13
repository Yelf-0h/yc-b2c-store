package com.yecheng.param;

import lombok.Data;

/**
 * 产品搜索参数
 *
 * @author Yefl
 * @date 2022/11/13
 */
@Data
public class ProductSearchParam extends PageParam {

    private String search;


//    /**
//     * 运算分页起始值
//     * @return
//     */
//    public int getFrom(){
//        return (currentPage-1)*pageSize;
//    }
//
//    /**
//     * 返回查询值
//     * @return
//     */
//    public int getSize(){
//        return pageSize;
//    }
}