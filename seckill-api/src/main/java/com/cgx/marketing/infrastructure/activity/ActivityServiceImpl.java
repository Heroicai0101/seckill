package com.cgx.marketing.infrastructure.activity;

import com.cgx.marketing.domain.model.activity.Activity;
import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.ActivityItem;
import com.cgx.marketing.domain.model.activity.repository.ActivityItemRepository;
import com.cgx.marketing.domain.model.activity.repository.ActivityRepository;
import com.cgx.marketing.domain.service.ActivityService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private ActivityItemRepository activityItemRepository;

    /**
     * 配置活动及商品列表
     */
    @Override
    public void saveActivity(Activity activity, List<ActivityItem> activityItems) {
        // 保存活动
        activityRepository.saveActivity(activity);

        // 保存活动商品
        ActivityId activityId = activity.getActivityId();
        activityItemRepository.saveActivityItem(activityId, activityItems);
    }

    /**
     * 启用/禁用活动
     */
    @Override
    public void enableActivity(ActivityId activityId, boolean enabled) {
        Activity activity = activityRepository.findActivity(activityId);
        Assert.notNull(activity, "活动不存在");

        activity.enableActivity(enabled);
        activityRepository.saveActivity(activity);
    }

}
