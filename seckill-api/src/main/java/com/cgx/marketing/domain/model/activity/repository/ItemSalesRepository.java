package com.cgx.marketing.domain.model.activity.repository;

import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.ItemId;
import com.cgx.marketing.domain.model.activity.ItemSales;

import java.util.Map;

/**
 * 商品销量
 */
public interface ItemSalesRepository {

    /**
     * 查活动全部商品销量
     */
    Map<Long, Integer> queryActivityItemSales(ActivityId activityId);

    /**
     * 查商品指定活动的销量
     */
    ItemSales queryItemSales(ActivityId activityId, ItemId itemId);

}
