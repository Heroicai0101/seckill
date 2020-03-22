package com.cgx.marketing.domain.model.activity.repository;

import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.ActivityItem;
import com.cgx.marketing.domain.model.activity.ItemId;

import java.util.List;
import java.util.Optional;

/**
 * 活动商品
 */
public interface ActivityItemRepository {

    /**
     * 保存指定活动的商品配置
     */
    void saveActivityItem(ActivityId activityId, List<ActivityItem> activityItems);

    /**
     * 查指定活动的指定商品
     */
    Optional<ActivityItem> findActivityItem(ActivityId activityId, ItemId itemId);

    /**
     * 查活动商品(缺商品销量)
     */
    List<ActivityItem> queryActivityItems(ActivityId activityId);

}
