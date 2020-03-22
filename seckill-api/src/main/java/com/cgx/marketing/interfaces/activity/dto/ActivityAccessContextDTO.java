package com.cgx.marketing.interfaces.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 活动准入上下文
 */
@Data
public class ActivityAccessContextDTO implements Serializable {

    private static final long serialVersionUID = -8007318200945094147L;

    @ApiModelProperty(value = "城市id", example = "11")
    private String cityId;

    @ApiModelProperty(value = "门店id", example = "123456")
    private String storeId;

}
