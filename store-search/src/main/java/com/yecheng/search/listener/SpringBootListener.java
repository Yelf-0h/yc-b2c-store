package com.yecheng.search.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yecheng.clients.ProductClient;
import com.yecheng.doc.ProductDoc;
import com.yecheng.pojo.Product;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-11-13-15:34
 * 监控boot程序启动，完成es数据的同步工作
 */
@Component
@Slf4j
public class SpringBootListener implements ApplicationRunner {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ProductClient productClient;

    private String indexStr = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"productId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productName\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"categoryId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productTitle\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productIntro\":{\n" +
            "        \"type\":\"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productPicture\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"productPrice\":{\n" +
            "        \"type\": \"double\",\n" +
            "        \"index\": true\n" +
            "      },\n" +
            "      \"productSellingPrice\":{\n" +
            "        \"type\": \"double\"\n" +
            "      },\n" +
            "      \"productNum\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productSales\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"all\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    /**
     * @param args
     * @throws Exception
     * 需要在此方法完成es的同步工作
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        /* 判断es中product索引是否存在 */
        GetIndexRequest getIndexRequest = new GetIndexRequest("product");
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (exists){
            /* 存在就删除原有数据 */
            DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest("product");
            /* 条件设置全部删除 */
            deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
            restHighLevelClient.deleteByQuery(deleteByQueryRequest,RequestOptions.DEFAULT);
        }else {
            /* 不存在创建新的索引表 product */
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("product");
            createIndexRequest.source(indexStr, XContentType.JSON);
            restHighLevelClient.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        }

        /* 查询全部商品数据 */
        List<Product> products = productClient.allList();
        /* 批量数据插入 */
        BulkRequest bulkRequest = new BulkRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Product product : products) {
            ProductDoc productDoc = new ProductDoc(product);
            /* 用于插入数据的作业 */
            IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());
            /* 将productDoc转成JSON放入 */
            String json = objectMapper.writeValueAsString(productDoc);
            indexRequest.source(json,XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);

    }
}
