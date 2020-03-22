package com.cgx.marketing.domain.model.activity.repository;

import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.OrderId;
import com.cgx.marketing.domain.model.activity.StockReduceFlow;

import java.util.Optional;

/**
 * 库存扣减流水
 */
public interface StockReduceFlowRepository {

    Optional<StockReduceFlow> queryStockReduceFlow(ActivityId activityId, OrderId orderId);

}
