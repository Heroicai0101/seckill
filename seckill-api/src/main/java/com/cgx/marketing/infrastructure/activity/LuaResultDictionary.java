package com.cgx.marketing.infrastructure.activity;

import com.cgx.marketing.common.enums.Status;
import com.cgx.marketing.common.enums.SystemCode;
import com.google.common.collect.Maps;
import lombok.NonNull;

import java.util.Map;

import static com.cgx.marketing.domain.shard.BizStatusCode.*;

public class LuaResultDictionary {

    public static final String SUCCESS_RESULT = "OK";

    private static Map<String, Status> code2Status;

    static {
        code2Status = Maps.newHashMap();
        code2Status.put(SUCCESS_RESULT, SystemCode.SUCCESS);
        code2Status.put("REPEATED_REQUEST", REPEATED_REQUEST);
        code2Status.put("ITEM_ACTIVITY_ABSENT", ITEM_ACTIVITY_ABSENT);
        code2Status.put("QUOTA_NOT_ENOUGH", QUOTA_NOT_ENOUGH);
        code2Status.put("STOCK_NOT_ENOUGH", STOCK_NOT_ENOUGH);
        code2Status.put("NO_REDUCE_FLOW", NO_REDUCE_FLOW);
    }

    public static Status mapping(@NonNull String errorCode) {
        return code2Status.get(errorCode);
    }

}
