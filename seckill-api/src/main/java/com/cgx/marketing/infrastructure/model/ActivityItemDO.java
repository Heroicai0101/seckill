package com.cgx.marketing.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityItemDO {

    private Long itemId;

    private String itemTitle;

    private String subTitle;

    private String itemImage;

    private Long itemPrice;

    private Long activityPrice;

    private Long activityId;

    private Integer quota;

    private Integer stock;

}
