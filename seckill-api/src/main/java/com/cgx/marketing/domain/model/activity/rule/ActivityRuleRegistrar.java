package com.cgx.marketing.domain.model.activity.rule;

import com.cgx.marketing.common.exception.CustomException;
import com.cgx.marketing.domain.shard.BizStatusCode;
import com.google.common.collect.Maps;
import org.springframework.util.Assert;

import java.util.Map;

public class ActivityRuleRegistrar {

    private static Map<String, Class<? extends ActivityRule>> ruleClassMap = Maps.newConcurrentMap();

    static {
        // 注册城市规则
        register(CityRule.INSTANCE);
    }

    private ActivityRuleRegistrar() {
        throw new IllegalStateException("No instance");
    }

    private static void register(ActivityRule activityRule) {
        Class<? extends ActivityRule> oldRule = ruleClassMap.put(activityRule.ruleName(), activityRule.getClass());
        Assert.isNull(oldRule, "活动规则名称重复:ruleName=" + activityRule.ruleName());
    }

    public static ActivityRule parseRule(ActivityRuleConfig config) {
        String configKey = config.getConfigKey();

        try {
            ActivityRule activityRule = ruleClassMap.get(configKey).newInstance();
            activityRule.decode(config);
            return activityRule;
        } catch (Exception e) {
            throw new CustomException(BizStatusCode.ACTIVITY_RULE_CONFIG_ERROR, configKey);
        }
    }

}
