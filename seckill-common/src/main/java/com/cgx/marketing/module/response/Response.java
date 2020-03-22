package com.cgx.marketing.module.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Http响应规范
 */
@Data
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -6757577370352594680L;

    @ApiModelProperty(value = "状态")
    private boolean success;

    @ApiModelProperty(value = "结果码")
    private int status;

    @ApiModelProperty(value = "结果信息")
    private String msg;

    @ApiModelProperty(value = "错误码")
    private String code;

    @ApiModelProperty(value = "返回数据")
    private T data;

}
