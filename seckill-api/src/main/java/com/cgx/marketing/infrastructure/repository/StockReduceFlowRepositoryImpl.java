package com.cgx.marketing.infrastructure.repository;

import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.OrderId;
import com.cgx.marketing.domain.model.activity.StockReduceFlow;
import com.cgx.marketing.domain.model.activity.repository.StockReduceFlowRepository;
import com.cgx.marketing.infrastructure.activity.SeckillNamespace;
import com.cgx.marketing.infrastructure.model.StockReduceFlowDO;
import com.cgx.marketing.infrastructure.repository.convert.StockReduceFlowConverter;
import com.google.gson.Gson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Optional;

@Repository
public class StockReduceFlowRepositoryImpl implements StockReduceFlowRepository {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private Gson gson;

    @Override
    public Optional<StockReduceFlow> queryStockReduceFlow(ActivityId activityId, OrderId orderId) {
        String stockReduceFlowHash = SeckillNamespace.stockReduceFlowHash(activityId.getId());

        RMap<String, String> stockReduceFlows = redissonClient.getMap(stockReduceFlowHash);
        String flow = stockReduceFlows.get(orderId.getId());
        if (StringUtils.isEmpty(flow)) {
            return Optional.empty();
        }

        StockReduceFlowDO reduceFlowDO = gson.fromJson(flow, StockReduceFlowDO.class);
        return Optional.of(StockReduceFlowConverter.fromDO(reduceFlowDO));
    }

}
