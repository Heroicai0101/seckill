package com.cgx.marketing.domain.model.activity.repository;

import com.cgx.marketing.domain.model.activity.Activity;
import com.cgx.marketing.domain.model.activity.ActivityId;

import java.util.List;

/**
 * 活动
 */
public interface ActivityRepository {

    /**
     * 查活动列表
     */
    List<Activity> listActivity();

    /**
     * 查单个活动
     */
    Activity findActivity(ActivityId activityId);

    /**
     * 保存活动
     */
    void saveActivity(Activity activity);

}
