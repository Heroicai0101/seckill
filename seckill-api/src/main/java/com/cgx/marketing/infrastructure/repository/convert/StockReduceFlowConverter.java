package com.cgx.marketing.infrastructure.repository.convert;

import com.cgx.marketing.domain.model.activity.*;
import com.cgx.marketing.infrastructure.model.StockReduceFlowDO;

public abstract class StockReduceFlowConverter {

    public static StockReduceFlowDO toDO(StockReduceFlow flow) {
        StockReduceFlowDO flowDO = new StockReduceFlowDO();
        flowDO.setActivityId(flow.getActivityId().getId());

        OrderInfo orderInfo = flow.getOrderInfo();
        flowDO.setItemId(orderInfo.getItemId().getId());
        flowDO.setQuantity(orderInfo.getQuantity());
        flowDO.setOrderId(orderInfo.getOrderId().getId());
        flowDO.setOrderTime(orderInfo.getOrderTime());

        flowDO.setBuyerId(flow.getBuyerId().getId());
        return flowDO;
    }

    public static StockReduceFlow fromDO(StockReduceFlowDO flowDO) {
        OrderInfo orderInfo = OrderInfo.builder()
                                       .orderId(new OrderId(flowDO.getOrderId()))
                                       .itemId(new ItemId(flowDO.getItemId()))
                                       .orderTime(flowDO.getOrderTime())
                                       .quantity(flowDO.getQuantity())
                                       .build();
        return StockReduceFlow.builder()
                              .activityId(new ActivityId(flowDO.getActivityId()))
                              .buyerId(new BuyerId(flowDO.getBuyerId()))
                              .orderInfo(orderInfo)
                              .build();
    }

}
