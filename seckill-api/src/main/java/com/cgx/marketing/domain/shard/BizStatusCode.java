package com.cgx.marketing.domain.shard;

import com.cgx.marketing.common.enums.Status;

/**
 * 应用自定义异常
 */
public enum BizStatusCode implements Status {

    ACTIVITY_RULE_CONFIG_ERROR(40001, "活动规则配置错误[%s]"),
    ACTIVITY_NOT_EXISTS(40002, "活动不存在[%s]"),
    ACTIVITY_OFFLINE(40003, "活动未开始或已结束[%s]"),

    REPEATED_REQUEST(40100, "重复请求[订单:%s]"),
    ITEM_ACTIVITY_ABSENT(40101, "商品未参与活动[订单:%s]"),
    QUOTA_NOT_ENOUGH(40102, "用户配额不足[订单:%s]"),
    STOCK_NOT_ENOUGH(40103, "商品库存不足[订单:%s]"),
    NO_REDUCE_FLOW(40104, "库存扣减流水不存在[订单:%s]");

    private final int status;

    private final String msg;

    BizStatusCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getCode() {
        return this.name();
    }

}
