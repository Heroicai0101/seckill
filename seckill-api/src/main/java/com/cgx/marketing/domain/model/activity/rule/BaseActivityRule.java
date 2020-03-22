package com.cgx.marketing.domain.model.activity.rule;

import com.cgx.marketing.common.exception.CustomException;
import com.cgx.marketing.domain.shard.BizStatusCode;
import lombok.Data;

@Data
public abstract class BaseActivityRule implements ActivityRule {

    private Long activityId;

    protected abstract String encodeConfigValue();

    protected abstract void decodeConfigValue(String value);

    @Override
    public ActivityRuleConfig encode() {
        return ActivityRuleConfig.builder()
                                 .activityId(activityId)
                                 .configKey(ruleName())
                                 .configValue(encodeConfigValue())
                                 .build();
    }

    @Override
    public void decode(ActivityRuleConfig config) {
        if (ruleName().equals(config.getConfigKey())) {
            this.activityId = config.getActivityId();
            decodeConfigValue(config.getConfigValue());
        } else {
            throw new CustomException(BizStatusCode.ACTIVITY_RULE_CONFIG_ERROR, config.toString());
        }
    }

}
