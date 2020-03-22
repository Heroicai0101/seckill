package com.cgx.marketing.domain.service;

import com.cgx.marketing.ApplicationStarter;
import com.cgx.marketing.common.exception.CustomException;
import com.cgx.marketing.domain.model.activity.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationStarter.class)
public class StockReduceServiceTest {

    @Resource
    private StockReduceService stockReduceService;

    private StockReduceFlow stockReduceFlow;

    private ActivityId activityId;

    private ItemId itemId;

    private OrderId orderId;

    private BuyerId buyerId;

    @Before
    public void setUp() throws Exception {
        activityId = new ActivityId(1L);
        itemId = new ItemId(53724L);
        orderId = new OrderId("11_0001");
        buyerId = new BuyerId("buyer_cgx_001");

        OrderInfo orderInfo = OrderInfo.builder()
                                       .itemId(itemId)
                                       .orderId(orderId)
                                       .orderTime(System.currentTimeMillis())
                                       .quantity(1)
                                       .build();

        stockReduceFlow = new StockReduceFlow();
        stockReduceFlow.setActivityId(activityId);
        stockReduceFlow.setBuyerId(buyerId);
        stockReduceFlow.setOrderInfo(orderInfo);
    }

    // 第一次成功，第二次异常
    @Test(expected = CustomException.class)
    public void testReduce() throws Exception {
        for (int i = 0; i < 2; i++) {
            stockReduceService.reduce(stockReduceFlow);
        }
    }

    // 第一次成功，第二次异常
    @Test(expected = CustomException.class)
    public void testCancelReduce() throws Exception {
        for (int i = 0; i < 2; i++) {
            stockReduceService.cancelReduce(stockReduceFlow);
        }
    }

}