package com.cgx.marketing.application.activity;

import com.cgx.marketing.domain.model.activity.StockReduceResult;
import com.cgx.marketing.interfaces.activity.dto.CancelReduceCommand;
import com.cgx.marketing.interfaces.activity.dto.ReduceCommand;

/**
 * 库存扣减应用服务
 */
public interface StockAppService {

    /**
     * 扣库存
     */
    StockReduceResult reduce(ReduceCommand command);

    /**
     * 回库存
     */
    StockReduceResult cancelReduce(CancelReduceCommand command);

}
