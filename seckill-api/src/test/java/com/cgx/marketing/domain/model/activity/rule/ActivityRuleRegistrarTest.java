package com.cgx.marketing.domain.model.activity.rule;

import com.cgx.marketing.common.exception.CustomException;
import org.junit.Assert;
import org.junit.Test;

public class ActivityRuleRegistrarTest {

    @Test
    public void parseRule() throws Exception {
        ActivityRuleConfig config = new ActivityRuleConfig();
        config.setActivityId(1L);
        config.setConfigKey("city");
        config.setConfigValue("1,2,3");

        ActivityRule activityRule = ActivityRuleRegistrar.parseRule(config);
        Assert.assertNotNull(activityRule);
    }

    @Test(expected = CustomException.class)
    public void parseRuleExceptionHappened() throws Exception {
        ActivityRuleConfig config = new ActivityRuleConfig();
        config.setActivityId(1L);
        config.setConfigKey("abc");
        config.setConfigValue("1,2,3");

        ActivityRule activityRule = ActivityRuleRegistrar.parseRule(config);
        Assert.assertNotNull(activityRule);
    }

}