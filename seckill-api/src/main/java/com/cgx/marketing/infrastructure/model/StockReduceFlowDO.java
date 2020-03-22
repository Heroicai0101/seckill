package com.cgx.marketing.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockReduceFlowDO {

    private String buyerId;

    private Long activityId;

    private String orderId;

    private Long orderTime;

    private Long itemId;

    private Integer quantity;

}
