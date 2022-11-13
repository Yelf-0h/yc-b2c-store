package com.yecheng.search.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yecheng.param.ProductSearchParam;
import com.yecheng.pojo.Product;
import com.yecheng.search.service.SearchService;
import com.yecheng.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yelf
 * @create 2022-11-13-16:52
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    /**
     * 根据关键字和分页进行数据库数据查询搜索产品
     *
     * @return {@link R}
     */
    @Override
    public R searchProduct(ProductSearchParam productSearchParam) {
        /* 2 创建searchRequest并设置针对那个表进行查询 */
        SearchRequest searchRequest = new SearchRequest("product");
        /* 3 去除参数中的查询关键字 */
        String search = productSearchParam.getSearch();
        /* 4 判断是否有存在关键字 */
        if (StringUtils.isEmpty(search)){
            /* 5 关键字为空查询全部，不添加关键字all*/
            searchRequest.source().query(QueryBuilders.matchAllQuery());
        }else {
            /* 6 关键字存在证明要去all的集合中查询存在的关键字 */
            searchRequest.source().query(QueryBuilders.matchQuery("all",search));
        }
        /* 7 进行分页数据添加*/
        /* from偏移量(当前页数-1)*页容量 */
        searchRequest.source().from((productSearchParam.getCurrentPage()-1)*productSearchParam.getPageSize());
        searchRequest.source().size(productSearchParam.getPageSize());
        SearchResponse searchResponse = null;
        try {
            /* 1 进行查询需要用到searchRequest ，所以去创建searchRequest */
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("查询错误",e);
        }

        /* 8 根据得到的searchResponse 进行解析 得到hits */
        SearchHits hits = searchResponse.getHits();

        /* 9 继续解析得到 商品服务 R 要返回msg total(符合的数量) data(商品集合) */
        long total = hits.getTotalHits().value;
        SearchHit[] hitsHits = hits.getHits();

        List<Product> productList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hitsHit : hitsHits) {
            /* 拿到productDoc模型的json数据 */
            String sourceAsString = hitsHit.getSourceAsString();
            Product product = null;
            try {
                /* sourceAsString是productDoc的模型所以多了一个all，需要去product中添加json注解
                *  @JsonIgnoreProperties(ignoreUnknown = true) 不存在的属性就不进行转换
                *  */
                product = objectMapper.readValue(sourceAsString, Product.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("读取json为product失败",e);
            }
            productList.add(product);

        }
        R ok = R.ok(null, productList, total);
        log.info("SearchServiceImpl.searchProduct业务结束，结果为：{}",ok);
        return ok;
    }
}
