package com.cgx.marketing.infrastructure.model;

import com.cgx.marketing.domain.model.activity.rule.ActivityRuleConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDO {

    private Long activityId;

    private String activityName;

    private Long presaleStartTime;

    private Long startTime;

    private Long endTime;

    private boolean enabled;

    private List<ActivityRuleConfig> activityRuleConfig;

}
