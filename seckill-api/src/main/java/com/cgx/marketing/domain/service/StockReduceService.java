package com.cgx.marketing.domain.service;

import com.cgx.marketing.domain.model.activity.StockReduceFlow;
import com.cgx.marketing.domain.model.activity.StockReduceResult;

/**
 * 库存扣减服务
 */
public interface StockReduceService {

    /**
     * 扣库存(同步调用)
     */
    StockReduceResult reduce(StockReduceFlow flow);

    /**
     * 回库存
     */
    StockReduceResult cancelReduce(StockReduceFlow flow);

}
