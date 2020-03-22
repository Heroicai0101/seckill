package com.cgx.marketing.common.enums;

/**
 * 错误码规范
 */
public interface Status {

    int getStatus();

    String getMsg();

    String getCode();

    default String getMsg(Object... extra) {
        String msg = getMsg();
        if (msg == null) {
            return "";
        }
        if (msg.contains("%s") && extra != null && extra.length > 0) {
            return String.format(msg, extra);
        }
        return msg.replace("%s", "").replace("[]", "");
    }

}
