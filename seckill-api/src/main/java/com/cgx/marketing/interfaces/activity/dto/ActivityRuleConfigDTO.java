package com.cgx.marketing.interfaces.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 活动准入规则配置
 */
@Data
public class ActivityRuleConfigDTO  implements Serializable {

    private static final long serialVersionUID = -6275415465597384043L;

    @ApiModelProperty(value = "活动规则key", example = "city")
    private String configKey;

    @ApiModelProperty(value = "活动规则value", example = "17,5,10,2,3,4,11")
    private String configValue;

}
