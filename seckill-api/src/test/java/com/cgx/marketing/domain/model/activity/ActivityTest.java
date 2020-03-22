package com.cgx.marketing.domain.model.activity;

import com.cgx.marketing.domain.model.activity.rule.*;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

public class ActivityTest {

    @Test
    public void canPass() throws Exception {
        Activity activity = new Activity();

        ActivityRuleConfig ruleConfig = new ActivityRuleConfig();
        ruleConfig.setActivityId(1L);
        ruleConfig.setConfigKey("city");
        ruleConfig.setConfigValue("1,2,3");

        ActivityRule activityRule = ActivityRuleRegistrar.parseRule(ruleConfig);
        activity.setActivityRules(Lists.newArrayList(activityRule));

        ActivityAccessContext context = new ActivityAccessContext();
        context.setCityId("1");

        ActivityRuleCheckResult okRes = activity.canPass(context);
        Assert.assertTrue(okRes.isPass());

        context.setCityId("999");
        ActivityRuleCheckResult errorRes = activity.canPass(context);
        Assert.assertFalse(errorRes.isPass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void canPassExceptionHappened() throws Exception {
        Activity activity = new Activity();

        ActivityRuleConfig ruleConfig = new ActivityRuleConfig();
        ruleConfig.setActivityId(1L);
        ruleConfig.setConfigKey("city");
        ruleConfig.setConfigValue("1,2,3");

        // 活动未配置规则, 准入上下文为空
        ActivityRuleCheckResult okRes = activity.canPass(null);
        Assert.assertTrue(okRes.isPass());

        // 活动配置了规则, 准入上下文不为空且符合条件
        ActivityRule activityRule = ActivityRuleRegistrar.parseRule(ruleConfig);
        activity.setActivityRules(Lists.newArrayList(activityRule));

        ActivityAccessContext context = new ActivityAccessContext();
        context.setCityId("1");
        ActivityRuleCheckResult okResAgain = activity.canPass(context);
        Assert.assertTrue(okResAgain.isPass());

        // 活动配置了规则, 准入上下文为空; 会报错
        ActivityRuleCheckResult errorRes = activity.canPass(null);
        Assert.assertFalse(errorRes.isPass());
    }

}