package com.cgx.marketing.domain.model.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {

    private OrderId orderId;

    private Long orderTime;

    private ItemId itemId;

    private Integer quantity;

}
