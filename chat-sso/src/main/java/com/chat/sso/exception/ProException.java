package com.chat.sso.exception;

/**
 * Created by :Guozhihua
 * Date： 2017/2/13.
 */
public class ProException extends Exception {
    private  String  exceptionCode ;
    public ProException(String message, Throwable cause, String exceptionCode) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }

    public ProException(String message, String exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
