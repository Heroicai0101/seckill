package com.cgx.marketing.infrastructure.activity;

import com.cgx.marketing.common.enums.Status;
import com.cgx.marketing.domain.model.activity.*;
import com.cgx.marketing.domain.service.StockReduceService;
import com.cgx.marketing.infrastructure.model.StockReduceFlowDO;
import com.cgx.marketing.infrastructure.repository.convert.StockReduceFlowConverter;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class StockReduceServiceImpl implements StockReduceService {

    @Resource
    private Gson gson;

    @Resource
    private RedissonClient redissonClient;

    private String reduceLua;

    private String cancelReduceLua;

    @PostConstruct
    public void scriptLoading() {
        try {
            reduceLua = LuaScriptHelper.readScript(LuaScriptConstant.Seckill.REDUCE_LUA);
            cancelReduceLua = LuaScriptHelper.readScript(LuaScriptConstant.Seckill.CANCEL_REDUCE_LUA);
        } catch (IOException ioe) {
            throw new IllegalStateException("Script not found!", ioe);
        }
    }

    /**
     * 执行Lua脚本扣库存
     */
    @Override
    public StockReduceResult reduce(StockReduceFlow flow) {
        Long activityId = flow.getActivityId().getId();
        Long itemId = flow.getOrderInfo().getItemId().getId();

        /* KEYS[1] 库存扣减流水, KEYS[2] 活动商品, KEYS[3] 买家已购, KEYS[4] 商品销量 */
        String stockReduceFlowHash = SeckillNamespace.stockReduceFlowHash(activityId);
        String activityItemHash = SeckillNamespace.activityItemsHash(activityId);
        String buyerHoldHash = SeckillNamespace.buyerHoldHash(activityId, itemId);
        String itemSalesHash = SeckillNamespace.itemSalesHash(activityId);
        List<Object> keys = Lists.newArrayList(stockReduceFlowHash, activityItemHash, buyerHoldHash, itemSalesHash);

        /* ARGV[1] 订单id, ARGV[2] 买家id, ARGV[3] 商品id, ARGV[4] 抢购数量, ARGV[5] json化库存扣减流水 */
        StockReduceFlowDO reduceFlowDO = StockReduceFlowConverter.toDO(flow);
        String reduceFlowJson = gson.toJson(reduceFlowDO);
        Object[] values = {
                reduceFlowDO.getOrderId(),
                reduceFlowDO.getBuyerId(),
                reduceFlowDO.getItemId(),
                reduceFlowDO.getQuantity(),
                reduceFlowJson
        };

        // 执行减库存Lua脚本
        String resultCode = LuaScriptHelper.create(redissonClient)
                                           .evalLuaScript(keys, values, reduceLua);

        if (!LuaResultDictionary.SUCCESS_RESULT.equals(resultCode)) {
            Status status = LuaResultDictionary.mapping(resultCode);
            String errmsg = status.getMsg(reduceFlowDO.getOrderId());
            log.error("reduce_exception||reduceFlow={}||errmsg={}", reduceFlowJson, errmsg);
            return StockReduceResult.error(errmsg);
        }

        log.info("reduce_success||reduceFlow={}", reduceFlowJson);
        return StockReduceResult.ok();
    }

    /**
     * 执行Lua脚本回库存
     */
    @Override
    public StockReduceResult cancelReduce(StockReduceFlow stockReduceFlow) {
        ActivityId activityId = stockReduceFlow.getActivityId();
        OrderInfo orderInfo = stockReduceFlow.getOrderInfo();
        OrderId orderId = orderInfo.getOrderId();
        ItemId itemId = orderInfo.getItemId();

        Long aid = activityId.getId();
        String stockReduceFlowHash = SeckillNamespace.stockReduceFlowHash(aid);
        String buyerHoldHash = SeckillNamespace.buyerHoldHash(aid, itemId.getId());
        String itemSalesHash = SeckillNamespace.itemSalesHash(aid);

        /* KEYS[1] 库存扣减流水, KEYS[2] 买家已购, KEYS[3] 商品销量 */
        List<Object> keys = Lists.newArrayList(stockReduceFlowHash, buyerHoldHash, itemSalesHash);

         /* ARGV[1] 订单id */
        Object[] values = {orderId.getId()};

        // 执行回库存Lua脚本
        String resultCode = LuaScriptHelper.create(redissonClient)
                                           .evalLuaScript(keys, values, cancelReduceLua);

        if (!LuaResultDictionary.SUCCESS_RESULT.equals(resultCode)) {
            Status status = LuaResultDictionary.mapping(resultCode);
            String errmsg = status.getMsg(orderId.getId());
            log.error("cancel_reduce_exception||activityId={}||orderId={}||itemId={}||errmsg={}",
                    aid, orderId.getId(), itemId.getId(), errmsg);

            return StockReduceResult.error(errmsg);
        }

        log.info("cancel_reduce_success||activityId={}||orderId={}||itemId={}", aid, orderId.getId(), itemId.getId());
        return StockReduceResult.ok();
    }

}
