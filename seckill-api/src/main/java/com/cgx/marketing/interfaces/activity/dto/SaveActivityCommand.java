package com.cgx.marketing.interfaces.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建/更新活动请求
 */
@Data
public class SaveActivityCommand implements Serializable {

    private static final long serialVersionUID = 6408124778959457636L;

    @ApiModelProperty(value = "活动id", example = "1")
    private Long activityId;

    @ApiModelProperty(value = "活动名称", example = "双十一秒杀第一场")
    private String activityName;

    @ApiModelProperty(value = "活动开始时间", example = "1541901600000")
    private Long startTime;

    @ApiModelProperty(value = "活动结束时间", example = "1741951999000")
    private Long endTime;

    @ApiModelProperty(value = "启用/禁用活动", example = "false")
    private boolean enabled;

    @ApiModelProperty(value = "活动商品列表")
    private List<ActivityItemDTO> itemLine;

    @ApiModelProperty(value = "活动规则，目前仅有城市规则")
    private List<ActivityRuleConfigDTO> activityRuleConfigs;

}
