package com.cgx.marketing.domain.model.activity.rule;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * 城市准入规则
 */
public class CityRule extends BaseActivityRule {

    public static final CityRule INSTANCE = new CityRule();

    private static final String NO_CITY_LIMITATION = "-1";

    private Set<String> cityIds = Sets.newHashSet();

    @Override
    public ActivityRuleCheckResult satisfy(ActivityAccessContext context) {
        String cityId = context.getCityId();
        Assert.hasText(cityId, "城市信息为空");

        if (cityIds.contains(NO_CITY_LIMITATION) || cityIds.contains(cityId)) {
            return ActivityRuleCheckResult.ok();
        }
        return ActivityRuleCheckResult.error("非活动城市");
    }

    @Override
    protected void decodeConfigValue(String value) {
        if (StringUtils.hasText(value)) {
            this.cityIds = Sets.newHashSet(Splitter.on(",").trimResults().splitToList(value));
        }
    }

    @Override
    protected String encodeConfigValue() {
        return Joiner.on(",").skipNulls().join(cityIds);
    }

    @Override
    public String ruleName() {
        return "city";
    }

}
