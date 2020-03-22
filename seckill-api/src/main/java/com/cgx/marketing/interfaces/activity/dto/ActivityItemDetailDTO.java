package com.cgx.marketing.interfaces.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 活动商品详情
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityItemDetailDTO implements Serializable {

    private static final long serialVersionUID = -5273660672501841904L;

    @ApiModelProperty(value = "商品id", example = "53724")
    private Long itemId;

    @ApiModelProperty(value = "商品标题", example = "x商品")
    private String itemTitle;

    @ApiModelProperty(value = "商品副标题", example = "x商品副标题")
    private String subTitle;

    @ApiModelProperty(value = "商品图片链接", example = "http://img.xxxx.com/static/do1_QtSq1m2xM7VL6zEI4sUH")
    private String itemImage;

    @ApiModelProperty(value = "商品原价", example = "19800")
    private Long itemPrice;

    @ApiModelProperty(value = "商品活动价", example = "4800")
    private Long activityPrice;

    @ApiModelProperty(value = "每人限购件数", example = "3")
    private Integer quota;

    @ApiModelProperty(value = "商品活动库存", example = "100")
    private Integer stock;

    @ApiModelProperty(value = "商品销量", example = "0")
    private Integer sold;

    @ApiModelProperty(value = "活动信息")
    private ActivityDTO activity;

}
