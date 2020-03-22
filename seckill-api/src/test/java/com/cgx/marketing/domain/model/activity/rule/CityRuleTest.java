package com.cgx.marketing.domain.model.activity.rule;

import org.junit.Assert;
import org.junit.Test;

public class CityRuleTest {

    @Test
    public void satisfy() throws Exception {
        ActivityAccessContext context = new ActivityAccessContext();
        context.setCityId("11");

        CityRule cityRule = new CityRule();
        cityRule.decodeConfigValue("11,12");

        ActivityRuleCheckResult passRes = cityRule.satisfy(context);
        Assert.assertTrue(passRes.isPass());

        cityRule.decodeConfigValue("1,2");
        ActivityRuleCheckResult noPassResult = cityRule.satisfy(context);
        Assert.assertFalse(noPassResult.isPass());
    }

}