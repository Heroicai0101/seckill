package com.cgx.marketing.domain.model.activity;

import com.cgx.marketing.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 库存扣减成功/失败
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockReduceResult implements Serializable {

    private static final long serialVersionUID = -8335167654710825203L;

    private boolean success;

    private String errmsg;

    public static StockReduceResult ok() {
        return new StockReduceResult(true, "OK");
    }

    public static StockReduceResult error(String errmsg) {
        return new StockReduceResult(false, errmsg);
    }

    public static StockReduceResult error(Status status, Object... extra) {
        return error(status.getMsg(extra));
    }

}
