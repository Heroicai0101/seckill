package com.cgx.marketing.module.response;

import com.cgx.marketing.common.enums.Status;
import com.cgx.marketing.common.enums.SystemCode;
import com.cgx.marketing.common.exception.CustomException;

public class ResponseBuilder {

    private ResponseBuilder() {
        throw new IllegalStateException("No instance");
    }

    public static <T> Response<T> ok() {
        return ok(null);
    }

    public static <T> Response<T> ok(T data) {
        Response<T> response = new Response<>();
        response.setSuccess(true);
        response.setStatus(SystemCode.SUCCESS.getStatus());
        response.setMsg(SystemCode.SUCCESS.getMsg());
        response.setCode(SystemCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> Response<T> error(Status status) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setStatus(status.getStatus());
        response.setMsg(status.getMsg());
        response.setCode(status.getCode());
        return response;
    }

    public static <T> Response<T> error(CustomException exception) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setStatus(exception.getStatus());
        response.setMsg(exception.getMsg());
        response.setCode(exception.getCode());
        return response;
    }

    public static <T> Response<T> error(Status status, String... extra) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setStatus(status.getStatus());
        response.setCode(status.getCode());
        response.setMsg(status.getMsg((Object[]) extra));
        return response;
    }

}
