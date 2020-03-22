package com.cgx.marketing.infrastructure.repository.convert;

import com.cgx.marketing.domain.model.activity.Activity;
import com.cgx.marketing.domain.model.activity.ActivityId;
import com.cgx.marketing.domain.model.activity.rule.ActivityRule;
import com.cgx.marketing.domain.model.activity.rule.ActivityRuleConfig;
import com.cgx.marketing.domain.model.activity.rule.ActivityRuleRegistrar;
import com.cgx.marketing.infrastructure.model.ActivityDO;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ActivityConverter {

    public static Activity fromDO(ActivityDO activityDO) {
        Activity activity = new Activity();
        activity.setActivityId(new ActivityId(activityDO.getActivityId()));
        activity.setActivityName(activityDO.getActivityName());
        activity.setStartTime(activityDO.getStartTime());
        activity.setEndTime(activityDO.getEndTime());
        activity.setEnabled(activityDO.isEnabled());

        // 反序列化活动规则
        List<ActivityRule> activityRules = asActivityRules(activityDO.getActivityRuleConfig());
        activity.setActivityRules(activityRules);
        return activity;
    }

    public static ActivityDO toDO(Activity activity) {
        ActivityDO activityDO = new ActivityDO();
        activityDO.setActivityId(activity.getActivityId().getId());
        activityDO.setActivityName(activity.getActivityName());
        activityDO.setStartTime(activity.getStartTime());
        activityDO.setEndTime(activity.getEndTime());
        activityDO.setEnabled(activity.isEnabled());

        // 序列化活动规则
        List<ActivityRuleConfig> ruleConfigs = asActivityRuleConfigs(activity.getActivityRules());
        activityDO.setActivityRuleConfig(ruleConfigs);
        return activityDO;
    }

    private static List<ActivityRuleConfig> asActivityRuleConfigs(List<ActivityRule> activityRules) {
        if (CollectionUtils.isEmpty(activityRules)) {
            return Lists.newArrayList();
        }
        return activityRules.stream()
                            .map(ActivityRule::encode)
                            .collect(Collectors.toList());
    }

    private static List<ActivityRule> asActivityRules(List<ActivityRuleConfig> activityRuleConfigs) {
        if (CollectionUtils.isEmpty(activityRuleConfigs)) {
            return Lists.newArrayList();
        }
        return activityRuleConfigs.stream()
                                  .map(ActivityRuleRegistrar::parseRule)
                                  .collect(Collectors.toList());
    }
}
