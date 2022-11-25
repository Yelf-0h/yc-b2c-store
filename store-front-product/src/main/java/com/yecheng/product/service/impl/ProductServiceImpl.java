package com.yecheng.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yecheng.clients.*;
import com.yecheng.param.*;
import com.yecheng.pojo.Category;
import com.yecheng.pojo.Picture;
import com.yecheng.product.service.PictureService;
import com.yecheng.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.product.mapper.ProductMapper;
import com.yecheng.pojo.Product;
import com.yecheng.product.service.ProductService;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private CartClient cartClient;
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private CollectClient collectClient;

    /**
     * 单类别名称 查询热门商品 至多7条
     *       1. 根据类别名称 调用 feign客户端访问类别服务获取类别的数据
     *       2. 成功 继续根据类别id查询商品数据  [热门 销售量倒序 查询7]
     *       3. 结果封装即可
     * @param categoryName 类别名称
     * @return {@link R}
     */
    @Override
    @Cacheable(value = "list.product",key = "#categoryName",cacheManager = "cacheManagerHour")
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
    @Cacheable(value = "list.product",key = "#productHotsParam.categoryName")
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
    @Cacheable(value = "list.category",key = "#root.methodName",cacheManager = "cacheManagerDay")
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
    @Cacheable(value = "list.product",key = "#productIdsParam.categoryID+'-'+#productIdsParam.currentPage+'-'+#productIdsParam.pageSize")
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
    @Cacheable(value = "product",key = "#productID")
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
    @Cacheable(value = "picture",key = "#productID")
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

    /**
     * 根据产品id集合查询商品信息
     *
     * @param productIds 产品id
     * @return {@link R}
     */
    @Cacheable(value = "list.product",key = "#productIds")
    @Override
    public R productIds(List<Integer> productIds) {
        List<Product> productList = listByIds(productIds);
        R ok = R.ok("类别信息查询成功！", productList);
        log.info("ProductServiceImpl.productIds业务结束，结果为：{}",ok);
        return ok;
    }

    /**
     * 购物车列表,根据ids查询商品集合
     *
     * @param productIds 产品id
     * @return {@link List}<{@link Product}>
     */
    @Override
    public List<Product> cartList(List<Integer> productIds) {
        List<Product> list = lambdaQuery().in(Product::getProductId, productIds).list();
        log.info("ProductServiceImpl.cartList业务结束，结果为：{}",list);
        return list;
    }

    /**
     * 修改库存，增加销售量
     *
     * @param orderToProductParamList 产品参数列表
     */
    @Override
    public void subNumber(List<OrderToProductParam> orderToProductParamList) {
        /* 将集合转成map productId orderToProduct */
        Map<Integer, OrderToProductParam> map = orderToProductParamList.stream().collect(Collectors.toMap(OrderToProductParam::getProductId, v -> v));
        /* 获取商品的id集合 */
        Set<Integer> productIds = map.keySet();
        /* 查询集合对应的商品集合 */
        List<Product> productList = listByIds(productIds);
        /* 修改商品信息 */
        List<Product> products = productList.stream().map(product -> {
            Integer num = map.get(product.getProductId()).getNum();
            product.setProductNum(product.getProductNum() - num);
            product.setProductSales(product.getProductSales() + num);
            return product;
        }).collect(Collectors.toList());

        /* 批量更新方法 */
        updateBatchById(products);
    }

    /**
     * 类别服务调用管理调用
     *
     * @param categoryId 类别id
     * @return {@link Long}
     */
    @Override
    public Long categoryCount(Integer categoryId) {
        Long count = lambdaQuery().eq(Product::getCategoryId, categoryId).count();
        log.info("ProductServiceImpl.categoryCount业务结束，结果为：{}",count);
        return count;
    }

    /**
     * 保存商品信息
     * 1.保存商品信息
     * 2.保存商品图片信息
     * 3.发送消息,es库进行插入
     *
     * @param productSaveParam 产品保存参数
     * @return {@link R}
     */
    @Override
    public R saveProduct(ProductSaveParam productSaveParam) {
        Product product = new Product();
        BeanUtils.copyProperties(productSaveParam,product);
        /* 保存 */
        boolean save = save(product);
        if (!save){
            log.info("ProductServiceImpl.saveProduct业务结束，结果为：{}","添加商品失败！");
            return R.fail("添加商品失败！");
        }

        /* 获取图片地址串进行截取 +分割的 */
        String pictures = productSaveParam.getPictures();
        if (!StringUtils.isEmpty(pictures)) {
            /* $ + - * | / ？^符号在正则表达示中有相应的不同意义。
               一般来讲只需要加[]、或是\\即可 */
            String[] urls = pictures.split("[+]");
            List<Picture> pictureList = new ArrayList<>();
            for (String url : urls) {
                Picture picture = new Picture();
                picture.setProductId(product.getProductId());
                picture.setProductPicture(url);
                pictureList.add(picture);
            }
            boolean b = pictureService.saveBatch(pictureList);
        }
        /* 同步搜索服务里的数据 */
        searchClient.saveOrUpdateProduct(product);

        return R.ok("添加商品数据成功！");
    }

    /**
     * 更新产品信息
     *
     * @param product 产品
     * @return {@link R}
     */
    @Override
    public R updateProduct(Product product) {
        boolean b = updateById(product);
        if (!b) {
            return R.fail("修改商品信息失败！");
        }
        searchClient.saveOrUpdateProduct(product);
        R ok = R.ok("修改商品信息成功！");
        log.info("ProductServiceImpl.updateProduct业务结束，结果为：{}",ok);
        return ok;
    }

    /**
     * 删除产品, 后台管理调用
     *
     * @param productId 产品id
     * @return {@link R}
     */
    @Override
    public R removeProduct(Integer productId) {
        /* 检查是否存在引用此商品的购物车 */
        Long checkCart = cartClient.adminCheckCart(productId);
        /* 检查是否存在引用此商品的订单 */
        Long checkOrder = orderClient.adminCheckOrder(productId);
        if (checkCart > 0){
            log.info("ProductServiceImpl.removeProduct业务结束，结果为删除商品失败！：{}个购物车项包含此商品",checkCart);
            return R.fail("删除商品失败！有："+checkCart+"个购物车项包含此商品！");
        }
        if (checkOrder > 0){
            log.info("ProductServiceImpl.removeProduct业务结束，结果为删除商品失败！：{}个订单包含此商品",checkOrder);
            return R.fail("删除商品失败！有："+checkOrder+"个订单包含此商品！");
        }
        /* 删除收藏 */
        R r = collectClient.adminRemoveCollect(productId);
        log.info("ProductServiceImpl.removeProduct业务结束，删除收藏结果为：{}",r);
        /* 删除商品 */
        boolean b = removeById(productId);
        if (!b){
            return R.fail("删除商品失败！");
        }
        /* 删除详情图 */
        LambdaQueryWrapper<Picture> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Picture::getProductId,productId);
        pictureService.remove(wrapper);
        /* 删除es中缓存 */
        searchClient.removeProduct(productId);
        log.info("ProductServiceImpl.removeProduct业务结束，结果为：{}","删除商品成功！");
        return R.ok("删除商品成功！");
    }
}
