package com.cgx.marketing.infrastructure.common.filter;

import com.cgx.marketing.common.enums.SystemCode;
import com.cgx.marketing.common.exception.CustomException;
import com.cgx.marketing.module.response.Response;
import com.cgx.marketing.module.response.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeoutException;

/**
 * 全局异常拦截器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 请求体json格式非法
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response<?> handleException(HttpMessageNotReadableException e, HttpServletRequest request) {
        Response<?> webBaseResponse = ResponseBuilder.error(SystemCode.JSON_FORMAT_ERROR);
        logException(e, webBaseResponse, request);
        return webBaseResponse;
    }

    /**
     * 参数类型不匹配：eg：接收Long，传入参数为abc
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Response<?> handleException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        String extra = e.getName() + "=" + e.getValue();
        Response<?> webBaseResponse = ResponseBuilder.error(SystemCode.PARAM_VALIDATE_ILLEGAL, extra);
        logException(e, webBaseResponse, request);
        return webBaseResponse;
    }

    /**
     * 请求缺少参数
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response<?> handleException(MissingServletRequestParameterException e, HttpServletRequest request) {
        Response<?> webBaseResponse = ResponseBuilder.error(SystemCode.PARAM_IS_EMPTY, e.getParameterName());
        logException(e, webBaseResponse, request);
        return webBaseResponse;
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Response<?> handleBusinessException(IllegalArgumentException e, HttpServletRequest request) {
        Response<?> webBaseResponse = ResponseBuilder.error(SystemCode.PARAM_VALIDATE_ILLEGAL, e.getMessage());
        logException(e, webBaseResponse, request);
        return webBaseResponse;
    }

    /**
     * 自定义异常，多为主动抛出需要引起重视的异常
     */
    @ExceptionHandler(CustomException.class)
    public Response<?> handleBusinessException(CustomException e, HttpServletRequest request) {
        Response<?> webBaseResponse = ResponseBuilder.error(e);
        logException(e, webBaseResponse, request);
        return webBaseResponse;
    }

    /**
     * 响应超时：异步调用设置了超时时间的场景下容易出现
     */
    @ExceptionHandler(TimeoutException.class)
    public Response<?> handleException(TimeoutException e, HttpServletRequest request) {
        Response<?> webBaseResponse = ResponseBuilder.error(SystemCode.SERVICE_RUN_ERROR);
        logException(e, webBaseResponse, request);
        return webBaseResponse;
    }

    @ExceptionHandler(NestedRuntimeException.class)
    public Response<?> handleNestedRuntimeException(NestedRuntimeException e, HttpServletRequest request) {
        Response<?> webBaseResponse = ResponseBuilder.error(SystemCode.SERVICE_RUN_ERROR);
        logException(e, webBaseResponse, request);
        return webBaseResponse;
    }

    /**
     * 未考虑到的异常，一旦出现，需检查下是否有bug
     */
    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception e, HttpServletRequest request) {
        Response<?> webBaseResponse = ResponseBuilder.error(SystemCode.SERVICE_RUN_ERROR);
        Throwable cause = e.getCause();
        if (cause != null && (cause instanceof CustomException)) {
            webBaseResponse = ResponseBuilder.error((CustomException) cause);
        }
        logException(e, webBaseResponse, request);
        return webBaseResponse;
    }

    private static void logException(Exception e, Response<?> response, HttpServletRequest request) {
        String msg = String.format("uri=%s||errno=%s||errmsg=%s",
                request.getRequestURI(), response.getStatus(), response.getMsg());
        log.error(msg, e);
    }

}
