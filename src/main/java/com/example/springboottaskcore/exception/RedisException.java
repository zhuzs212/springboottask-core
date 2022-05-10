package com.example.springboottaskcore.exception;

public class RedisException extends RuntimeException {

    private static final long serialVersionUID = -9161348021156852454L;

    /* 错误码,用于返回接口code */
    private String errorCode;
    private String errorMessage;
    private RedisError error;

    public RedisException(String message) {
    }

    public RedisException(RedisError e) {
        super(e.getErrorMessage());
        this.errorCode = e.getErrorCode();
        this.errorMessage = e.getErrorMessage();
        this.error = e;
    }

    public RedisException(RedisError e, String msg) {
        super(msg);
        this.errorCode = e.getErrorCode();
        this.errorMessage = msg;
        this.error = e;
    }

    public RedisException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.errorMessage = msg;
    }

    public RedisException(RedisError ie, Exception e) {
        super(ie.getErrorMessage());
        this.errorCode = ie.getErrorCode();
        this.errorMessage = e.getMessage();
        this.error = ie;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public RedisError getError() {
        return error;
    }

    public void setError(RedisError error) {
        this.error = error;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }

    @Override
    public String getMessage() {
        return getErrorMessage();
    }
}
