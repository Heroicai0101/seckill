package com.cgx.marketing.interfaces.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 扣库存请求
 */
@Data
public class ReduceCommand implements Serializable {

    private static final long serialVersionUID = -3144660974342415658L;

    @ApiModelProperty(value = "活动id", example = "1")
    private Long activityId;

    @ApiModelProperty(value = "买家id", example = "buyer_001")
    private String buyerId;

    @ApiModelProperty(value = "订单id", example = "20181111123456789")
    private String orderId;

    @ApiModelProperty(value = "下单时间", example = "1541901700000")
    private Long orderTime;

    @ApiModelProperty(value = "商品id", example = "53724")
    private Long itemId;

    @ApiModelProperty(value = "购买商品数量", example = "1")
    private Integer quantity;

    @ApiModelProperty(value = "活动准入上下文")
    private ActivityAccessContextDTO activityAccessContext;

}
