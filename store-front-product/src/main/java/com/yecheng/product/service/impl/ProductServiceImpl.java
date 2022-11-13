package com.yecheng.product.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yecheng.clients.CategoryClient;
import com.yecheng.clients.SearchClient;
import com.yecheng.param.ProductHotsParam;
import com.yecheng.param.ProductIdsParam;
import com.yecheng.param.ProductSearchParam;
import com.yecheng.pojo.Category;
import com.yecheng.pojo.Picture;
import com.yecheng.product.service.PictureService;
import com.yecheng.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.product.mapper.ProductMapper;
import com.yecheng.pojo.Product;
import com.yecheng.product.service.ProductService;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * (Product)表服务实现类
 *
 * @author makejava
 * @since 2022-11-12 23:42:37
 */
@Service("productService")
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    /**
     * 引入feign客户端需要在启动类添加配置注解
     */
    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SearchClient searchClient;

    @Autowired
    private PictureService pictureService;

    /**
     * 单类别名称 查询热门商品 至多7条
     *       1. 根据类别名称 调用 feign客户端访问类别服务获取类别的数据
     *       2. 成功 继续根据类别id查询商品数据  [热门 销售量倒序 查询7]
     *       3. 结果封装即可
     * @param categoryName 类别名称
     * @return {@link R}
     */
    @Override
    public R promoList(String categoryName) {
        /* 1. 根据类别名称 调用 feign客户端访问类别服务获取类别的数据 */
        R r = categoryClient.byName(categoryName);
        if (r.getCode().equals(R.FAIL_CODE)) {
            log.info("ProductServiceImpl.promoList业务结束，结果为：{}","类别查询失败");
            return r;
        }
        /* 类别服务中 data = category --- feign {json}  ----- product服务 LinkedHashMap jackson*/
        LinkedHashMap<String,Object> map = (LinkedHashMap<String, Object>) r.getData();
        Integer categoryId = (Integer) map.get("category_id");
        /* 2. 成功 继续根据类别id查询商品数据  [热门 销售量倒序 查询7] */
        Page<Product> page = lambdaQuery().eq(Product::getCategoryId, categoryId)
                .orderByDesc(Product::getProductSales)
                .page(new Page<>(1, 7));
        log.info("ProductServiceImpl.promoList业务结束，结果为：{}",page.getRecords());
        return R.ok("数据查询成功",page.getRecords());
    }

    /**
     * 热点列表
     * 多类别热门商品查询 根据类别名称集合! 至多查询7条!
     * 1. 调用类别服务
     * 2. 类别集合id查询商品
     * 3. 结果集封装即可
     *
     * @param productHotsParam 产品还有参数
     * @return {@link R}
     */
    @Override
    public R hotsList(ProductHotsParam productHotsParam) {
        /* 1. 调用类别服务 */
        R r = categoryClient.hotsCategory(productHotsParam);
        if (r.getCode().equals(R.FAIL_CODE)) {
            log.info("ProductServiceImpl.hotsList业务结束，结果为：{}",r.getMsg());
            return r;
        }
        List<Integer> ids = (List<Integer>) r.getData();
        /* 2. 类别集合id查询商品 */
        Page<Product> page = lambdaQuery().in(Product::getCategoryId, ids)
                .orderByDesc(Product::getProductSales)
                .page(new Page<>(1, 7));
        /* 3. 结果集封装即可 */
        List<Product> products = page.getRecords();
        R ok = R.ok("多类别热门商品查询成功！", products);
        log.info("ProductServiceImpl.hotsList业务结束，结果为：{}",ok);
        return ok;
    }

    /**
     * 查询类别列表进行返回
     *
     * @return {@link R}
     */
    @Override
    public R categoryList() {
        R r = categoryClient.listCategory();
        log.info("ProductServiceImpl.categoryList业务结束，结果为：{}",r);
        return r;
    }

    /**
     * 类别商品查询 前端传递类别集合
     *
     * @param productIdsParam 产品id参数
     * @return {@link R}
     */
    @Override
    public R byCategory(ProductIdsParam productIdsParam) {
        List<Integer> categoryID = productIdsParam.getCategoryID();
        Integer currentPage = productIdsParam.getCurrentPage();
        Integer pageSize = productIdsParam.getPageSize();
        Page<Product> page = lambdaQuery().in(!categoryID.isEmpty(), Product::getCategoryId, categoryID)
                .page(new Page<>(currentPage, pageSize));
        /* 结果集封装返回 */
        R ok = R.ok("查询成功！", page.getRecords(), page.getTotal());
        log.info("ProductServiceImpl.byCategory业务结束，结果为：{}",ok);
        return ok;
    }

    /**
     * 查询商品详情根据商品id
     *
     * @param productID 产品id
     * @return {@link R}
     */
    @Override
    public R detail(Integer productID) {
        Product product = getById(productID);
        R ok = R.ok(product);
        log.info("ProductServiceImpl.detail业务结束，结果为：{}",ok);
        return ok;
    }

    /**
     * 查询商品图片详情根据商品id
     *
     * @param productID 产品id
     * @return {@link R}
     */
    @Override
    public R pictures(Integer productID) {
        List<Picture> pictures = pictureService.lambdaQuery().eq(Picture::getProductId, productID).list();
        R ok = R.ok(pictures);
        log.info("ProductServiceImpl.pictures业务结束，结果为：{}",ok);
        return ok;
    }

    /**
     * 搜索服务调用，查询全部商品信息
     * 进行同步
     *
     * @return {@link List}<{@link Product}>
     */
    @Override
    public List<Product> allList() {
        List<Product> list = list();
        log.info("ProductServiceImpl.allList业务结束，结果为：{}",list.size());
        return list;
    }

    /**
     * 搜索业务，需要调用搜索服务
     *
     * @param productSearchParam 产品搜索参数
     * @return {@link R}
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {
        R r = searchClient.searchProduct(productSearchParam);
        log.info("ProductServiceImpl.search业务结束，结果为：{}",r);
        return r;
    }
}
