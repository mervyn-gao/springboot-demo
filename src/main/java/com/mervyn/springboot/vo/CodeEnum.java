package com.mervyn.springboot.vo;

/**
 * Created by mengran.gao on 2017/7/12.
 * code编码规则：2:服务级别错误 001:模块 01:具体错误
 */
public enum CodeEnum {

    //通用请求成功
    SUCCESS(200, "common.success"),
    //服务器内部错误
    SERVER_INNER_ERROR(500, "server.inner.error");

    private int code;
    private String message;

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
