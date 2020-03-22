package com.cgx.marketing.common.enums;

/**
 * 通用系统异常码
 */
public enum SystemCode implements Status {

    SUCCESS(10000, "OK"),
    PARAM_VALIDATE_ILLEGAL(10001, "参数非法[%s]"),
    PARAM_IS_EMPTY(10002, "参数缺失[%s]"),
    JSON_FORMAT_ERROR(10003, "Json格式不正确"),
    DATA_NOT_EXIST(10004, "数据不存在[%s]"),
    RPC_CALL_ERROR(10005, "服务调用异常[%s]"),
    DB_ACCESS_ERROR(80000, "数据库访问异常"),
    SERVICE_RUN_ERROR(99999, "服务器忙,请稍后再试");

    private int status;

    private String msg;

    SystemCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @Override
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getMsg(Object... extra) {
        if (msg == null) {
            return "";
        }
        if (msg.contains("%s") && extra != null) {
            return String.format(msg, extra);
        }
        return msg.replace("%s", "").replace("[]", "");
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return name();
    }

}
