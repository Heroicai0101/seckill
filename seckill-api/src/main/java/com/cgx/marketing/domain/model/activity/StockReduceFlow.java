package com.cgx.marketing.domain.model.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 库存扣减流水：记录在哪个活动(activityId)、哪个用户(buyerId)、在何时下了哪笔订单、拍下哪个商品多少个(orderInfo)
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockReduceFlow {

    /** 活动id */
    private ActivityId activityId;

    /** 买家id */
    private BuyerId buyerId;

    /** 订单信息 */
    private OrderInfo orderInfo;

}
