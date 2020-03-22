package com.cgx.marketing.domain.model.activity.rule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动准入上下文
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityAccessContext {

    private String cityId;

    private String storeId;

}
