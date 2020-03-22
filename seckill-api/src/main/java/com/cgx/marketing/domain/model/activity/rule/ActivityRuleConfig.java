package com.cgx.marketing.domain.model.activity.rule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动规则配置文件
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRuleConfig {

    private Long activityId;

    /** 对应ruleName */
    private String configKey;

    private String configValue;

}