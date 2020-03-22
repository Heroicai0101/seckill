package com.cgx.marketing.application.activity;

import com.cgx.marketing.BaseJUnitTest;
import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.ItemId;
import com.cgx.marketing.domain.model.activity.StockReduceResult;
import com.cgx.marketing.interfaces.activity.dto.ActivityAccessContextDTO;
import com.cgx.marketing.interfaces.activity.dto.CancelReduceCommand;
import com.cgx.marketing.interfaces.activity.dto.ReduceCommand;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class StockAppServiceTest extends BaseJUnitTest {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1000);

    private final int REQUEST_COUNT = 100000;

    private final AtomicInteger success = new AtomicInteger(0);

    private final AtomicInteger fail = new AtomicInteger(0);

    private final AtomicInteger sold = new AtomicInteger(0);

    @Resource
    private StockAppService stockReduceAppService;

    private ActivityId activityId;

    private ItemId itemId;

    @Before
    public void setUp() throws Exception {
        activityId = new ActivityId(1L);
        itemId = new ItemId(53724L);
    }

    /**
     * 模拟1000个用户10万次扣库存请求; 耗时约32秒
     */
    @Test
    public void reduce() throws Exception {
        ActivityAccessContextDTO activityAccessContext = new ActivityAccessContextDTO();
        activityAccessContext.setCityId("11");

        ThreadLocalRandom random = ThreadLocalRandom.current();
        long now = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(REQUEST_COUNT);
        for (int i = 0; i < REQUEST_COUNT; i++) {
            ReduceCommand command = new ReduceCommand();
            command.setActivityId(activityId.getId());
            command.setItemId(itemId.getId());
            command.setBuyerId("buyer_" + 100000 + i);
            command.setOrderId("20181111000" + i);
            command.setQuantity(random.nextInt(2) + 1);
            command.setOrderTime(System.currentTimeMillis());
            command.setActivityAccessContext(activityAccessContext);

            executorService.execute(new StockReduceTask(command, countDownLatch));
        }
        countDownLatch.await();
        log.info("success={}||fail={}||sold={}||cost={}", success.get(), fail.get(), sold.get(),
                System.currentTimeMillis() - now);
        Assert.assertTrue(sold.get() == 50);
    }

    /**
     * 模拟1000个用户10万次回库存请求; 耗时约7秒
     */
    @Test
    public void cancelReduce() throws Exception {
        long now = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(REQUEST_COUNT);
        for (int i = 0; i < REQUEST_COUNT; i++) {
            CancelReduceCommand command = new CancelReduceCommand();
            command.setActivityId(activityId.getId());
            command.setOrderId("20181111000" + i);

            executorService.execute(new StockCancelReduceTask(command, countDownLatch));
        }
        countDownLatch.await();
        log.info("success={}||fail={}||cost={}", success.get(), fail.get(), System.currentTimeMillis() - now);
        Assert.assertTrue(success.get() == REQUEST_COUNT);
    }

    class StockReduceTask implements Runnable {

        private ReduceCommand reduceCommand;

        private CountDownLatch countDownLatch;

        public StockReduceTask(ReduceCommand reduceCommand, CountDownLatch countDownLatch) {
            this.reduceCommand = reduceCommand;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                StockReduceResult res = stockReduceAppService.reduce(reduceCommand);
                if (res.isSuccess()) {
                    success.incrementAndGet();
                    sold.addAndGet(reduceCommand.getQuantity());
                }
            } catch (Exception e) {
                fail.incrementAndGet();
                log.error("order={}||buyer={}||msg={}", reduceCommand.getOrderId(), reduceCommand.getBuyerId(), e);
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    class StockCancelReduceTask implements Runnable {

        private CancelReduceCommand command;

        private CountDownLatch countDownLatch;

        public StockCancelReduceTask(CancelReduceCommand command, CountDownLatch countDownLatch) {
            this.command = command;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                StockReduceResult res = stockReduceAppService.cancelReduce(command);
                if (res.isSuccess()) {
                    success.incrementAndGet();
                }
            } catch (Exception e) {
                fail.incrementAndGet();
                log.error("activityId={}||orderId={}||msg={}", command.getActivityId(), command.getOrderId(), e);
            } finally {
                countDownLatch.countDown();
            }
        }
    }

}