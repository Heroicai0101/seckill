package com.cgx.marketing.domain.model.activity;

import lombok.Value;

/**
 * 商品销量
 */
@Value
public class ItemSales {

    private Long itemId;

    private int sold;

}
