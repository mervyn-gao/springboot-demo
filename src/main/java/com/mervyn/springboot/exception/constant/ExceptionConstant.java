package com.mervyn.springboot.exception.constant;

/**
 * Created by mengran.gao on 2017/7/7.
 */
public enum ExceptionConstant {

    ;// UNKNOW(500, "系统异常");

    private Integer code;

    private String message;

    ExceptionConstant(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessage(Integer code) {
        for (ExceptionConstant ec : values()) {
            if (ec.getCode().equals(code)) {
                return ec.getMessage();
            }
        }
        return null;
    }
}
