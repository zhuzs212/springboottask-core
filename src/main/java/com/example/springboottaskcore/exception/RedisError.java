package com.example.springboottaskcore.exception;

public enum RedisError {

    REDIS_CONN_EXCEPTION("9000", "获取redis连接池错误"),
    REDIS_PARAM_EXCEPTION("9001", "redis参数错误");

    private String errorCode = null;
    private String errorMessage = null;

    RedisError(String errCode, String errMessage) {
        this.errorCode = errCode;
        this.errorMessage = errMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
