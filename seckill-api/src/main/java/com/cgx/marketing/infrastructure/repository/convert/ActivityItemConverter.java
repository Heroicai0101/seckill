package com.cgx.marketing.infrastructure.repository.convert;


import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.ActivityItem;
import com.cgx.marketing.domain.model.activity.ItemId;
import com.cgx.marketing.infrastructure.model.ActivityItemDO;

public abstract class ActivityItemConverter {

    public static ActivityItem fromItemDO(ActivityItemDO itemDO) {
        ActivityItem item = new ActivityItem();
        item.setItemId(new ItemId(itemDO.getItemId()));
        item.setActivityId(new ActivityId(itemDO.getActivityId()));

        item.setActivityPrice(itemDO.getActivityPrice());
        item.setItemPrice(itemDO.getItemPrice());
        item.setItemImage(itemDO.getItemImage());
        item.setItemTitle(itemDO.getItemTitle());
        item.setSubTitle(itemDO.getSubTitle());
        item.setQuota(itemDO.getQuota());
        item.setStock(itemDO.getStock());
        return item;
    }

    public static ActivityItemDO toItemDO(ActivityItem item) {
        ActivityItemDO itemDO = new ActivityItemDO();
        itemDO.setItemId(item.getItemId().getId());
        itemDO.setActivityId(item.getActivityId().getId());

        itemDO.setActivityPrice(item.getActivityPrice());
        itemDO.setItemPrice(item.getItemPrice());
        itemDO.setItemImage(item.getItemImage());
        itemDO.setItemTitle(item.getItemTitle());
        itemDO.setSubTitle(item.getSubTitle());
        itemDO.setQuota(item.getQuota());
        itemDO.setStock(item.getStock());
        return itemDO;
    }

}
