import com.yecheng.order.OrderApplication;
import com.yecheng.order.service.OrdersService;
import com.yecheng.param.PageParam;
import com.yecheng.utils.R;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author Yelf
 * @create 2022-11-25-20:34
 */
@SpringBootTest(classes = OrderApplication.class)
public class ServiceTest {

    @Resource
    private OrdersService ordersService;

    @Test
    public void test1(){
        PageParam pageParam = new PageParam();
        R r = ordersService.pageOrder(pageParam);
        System.out.println(r);
    }

}
