package com.cgx.marketing.interfaces.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 回库存请求
 */
@Data
public class CancelReduceCommand implements Serializable {

    private static final long serialVersionUID = -947341931720784788L;

    @ApiModelProperty(value = "活动id", example = "1")
    private Long activityId;

    @ApiModelProperty(value = "订单id", example = "20181111123456789")
    private String orderId;

}
