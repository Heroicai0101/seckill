package com.cgx.marketing.infrastructure.activity;

public abstract class SeckillNamespace {

    public static final String ACTIVITY_ID = "activity_id";

    private static final String HASH_TAG_PREFIX = "{seckill_%s}";

    private static final String ACTIVITY_CATALOG_HASH = "activity_catalog";

    public static String activityCatalogHash() {
        return ACTIVITY_CATALOG_HASH;
    }

    public static String activityItemsHash(Long activityId) {
        return seckillHashTag(activityId) + String.format("activity_items:%s", activityId);
    }

    public static String buyerHoldHash(Long activityId, Long itemId) {
        return seckillHashTag(activityId) + String.format("buyer_hold:%s:%s", activityId, itemId);
    }

    public static String itemSalesHash(Long activityId) {
        return seckillHashTag(activityId) + String.format("item_sales:%s", activityId);
    }

    public static String stockReduceFlowHash(Long activityId) {
        return seckillHashTag(activityId) + String.format("stock_reduce_flow:%s", activityId);
    }

    private static String seckillHashTag(Long activityId) {
        return String.format(HASH_TAG_PREFIX, activityId);
    }

}
