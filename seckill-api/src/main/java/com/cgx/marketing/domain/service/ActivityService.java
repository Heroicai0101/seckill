package com.cgx.marketing.domain.service;

import com.cgx.marketing.domain.model.activity.Activity;
import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.ActivityItem;

import java.util.List;

public interface ActivityService {

    /**
     * 配置活动及活动商品
     */
    void saveActivity(Activity activity, List<ActivityItem> activityItems);

    /**
     * 启用/禁用活动
     */
    void enableActivity(ActivityId activityId, boolean enabled);

}
