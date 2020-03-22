package com.cgx.marketing.interfaces.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 活动详情
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDetailDTO implements Serializable {

    private static final long serialVersionUID = -4418910817073363086L;

    @ApiModelProperty(value = "活动id", example = "1")
    private Long activityId;

    @ApiModelProperty(value = "活动名称", example = "双十一秒杀第一场")
    private String activityName;

    @ApiModelProperty(value = "活动开始时间", example = "1541901600000")
    private Long startTime;

    @ApiModelProperty(value = "活动结束时间", example = "1741951999000")
    private Long endTime;

    @ApiModelProperty(value = "活动启用/禁用", example = "true")
    private boolean enabled;

    @ApiModelProperty(value = "活动商品")
    private List<ActivityItemDTO> items;

}
