package com.satrt.springweb.exception.service;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/26 18:00
 */
public class UserOperate extends Exception{
    /**
     *
     */
    private int code;

    public UserOperate(int code) {
        this.code = code;
    }

    public UserOperate(String message, int code) {
        super(message);
        this.code = code;
    }

    public UserOperate(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public UserOperate(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
