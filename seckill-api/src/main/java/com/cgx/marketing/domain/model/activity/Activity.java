package com.cgx.marketing.domain.model.activity;

import com.cgx.marketing.domain.model.activity.rule.ActivityAccessContext;
import com.cgx.marketing.domain.model.activity.rule.ActivityRule;
import com.cgx.marketing.domain.model.activity.rule.ActivityRuleCheckResult;
import lombok.Data;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 活动信息
 */
@Data
public class Activity {

    /** 活动id */
    private ActivityId activityId;

    /** 活动名称 */
    private String activityName;

    /** 活动开始时间 */
    private Long startTime;

    /** 活动结束时间 */
    private Long endTime;

    /** 活动是否启用 */
    private boolean enabled;

    /** 活动准入规则 */
    private List<ActivityRule> activityRules;

    /**
     * 活动进行中
     */
    public boolean onSale(Long orderTime) {
        return enabled && (orderTime >= startTime && orderTime < endTime);
    }

    /**
     * 启用/禁用活动
     */
    public void enableActivity(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 活动准入规则校验
     * 1、活动未配置规则, 则无需校验
     * 2、活动若配置了规则, 则逐一进行校验
     */
    public ActivityRuleCheckResult canPass(ActivityAccessContext context) {
        if (CollectionUtils.isEmpty(activityRules)) {
            return ActivityRuleCheckResult.ok();
        }

        Assert.notNull(context, "活动准入条件为空");
        for (ActivityRule activityRule : activityRules) {
            ActivityRuleCheckResult result = activityRule.satisfy(context);
            if (!result.isPass()) {
                return result;
            }
        }
        return ActivityRuleCheckResult.ok();
    }

}
