package com.cgx.marketing.interfaces.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 启用/禁用活动请求
 */
@Data
public class UpdateActivityStatusCommand implements Serializable {

    private static final long serialVersionUID = 7683289681966955369L;

    @ApiModelProperty(value = "活动id", example = "1")
    private Long activityId;

    @ApiModelProperty(value = "启用/禁用活动", example = "true")
    private boolean enabled;

}
