package com.cgx.marketing.interfaces.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 活动信息
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO implements Serializable {

    private static final long serialVersionUID = -3123560671992164117L;

    @ApiModelProperty(value = "活动id", example = "1")
    private Long activityId;

    @ApiModelProperty(value = "活动名称", example = "双十一秒杀第一场")
    private String activityName;

    @ApiModelProperty(value = "活动开始时间", example = "1541901600000")
    private Long startTime;

    @ApiModelProperty(value = "活动结束时间", example = "1741951999000")
    private Long endTime;

    @ApiModelProperty(value = "启用/禁用", example = "true")
    private boolean enabled;

}
