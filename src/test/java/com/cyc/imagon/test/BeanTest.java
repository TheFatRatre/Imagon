package com.cyc.imagon.test;

import com.cyc.imagon.mapper.MainModuleDataMapper;
import com.cyc.imagon.service.MainModuleDataService;
import com.cyc.imagon.utils.SpringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName: BeanTest
 * Package: com.cyc.imagon.test
 * Description:
 *
 * @Author CYC
 * @Create 2024/11/1 10:58
 * @Version 1.0
 */
@MapperScan("com.cyc.imagon.mapper")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MainModuleDataService.class, MainModuleDataMapper.class})
public class BeanTest {
    @Test
    public void contextLoads() {
        //GoodsServiceImpl为我想要获取的service层中的类
//        GoodsServiceImpl goodsServiceImpl = (GoodsServiceImpl) SpringUtil.getBean(GoodsServiceImpl.class);
//        System.out.println(goodsServiceImpl.getGoodsByID(27).getGoodsName());
        MainModuleDataService mainModuleDataService =  SpringUtil.getBean(MainModuleDataService.class);
        System.out.println(mainModuleDataService.count());
    }
}
