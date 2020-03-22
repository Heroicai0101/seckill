package com.cgx.marketing.domain.model.activity;

import lombok.Data;

/**
 * 活动商品
 */
@Data
public class ActivityItem {

    /** 商品id */
    private ItemId itemId;

    /** 活动id */
    private ActivityId activityId;

    /** 商品标题 */
    private String itemTitle;

    /** 商品副标题 */
    private String subTitle;

    /** 商品图片链接 */
    private String itemImage;

    /** 商品原价 */
    private Long itemPrice;

    /** 商品活动价 */
    private Long activityPrice;

    /** 每人限购件数 */
    private Integer quota;

    /** 商品活动库存 */
    private Integer stock;

}
