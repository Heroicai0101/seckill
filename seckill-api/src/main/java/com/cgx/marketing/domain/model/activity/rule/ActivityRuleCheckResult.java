package com.cgx.marketing.domain.model.activity.rule;

import lombok.Value;

@Value
public class ActivityRuleCheckResult {

    private boolean pass;

    private String errmsg;

    public static ActivityRuleCheckResult ok() {
        return new ActivityRuleCheckResult(true, "");
    }

    public static ActivityRuleCheckResult error(String errmsg) {
        return new ActivityRuleCheckResult(false, errmsg);
    }

}
