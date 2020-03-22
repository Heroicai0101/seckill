package com.cgx.marketing.application.activity;

import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.ItemId;
import com.cgx.marketing.interfaces.activity.dto.*;

import java.util.List;

public interface ActivityAppService {

    /**
     * 配置活动及商品列表
     */
    Long saveActivity(SaveActivityCommand command);

    /**
     * 启用/禁用活动
     */
    void changeActivityStatus(UpdateActivityStatusCommand command);

    /**
     * 活动列表
     */
    List<ActivityDTO> activityList();

    /**
     * 活动详情页(透出活动商品及商品销量)
     */
    ActivityDetailDTO activityDetail(Long activityId);

    /**
     * 活动商品详情
     */
    ActivityItemDetailDTO activityItemDetail(ActivityId activityId, ItemId itemId);

}
