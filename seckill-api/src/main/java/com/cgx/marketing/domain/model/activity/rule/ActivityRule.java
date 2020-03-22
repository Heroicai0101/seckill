package com.cgx.marketing.domain.model.activity.rule;

public interface ActivityRule {

    String ruleName();

    void decode(ActivityRuleConfig config);

    ActivityRuleConfig encode();

    ActivityRuleCheckResult satisfy(ActivityAccessContext context);

}
