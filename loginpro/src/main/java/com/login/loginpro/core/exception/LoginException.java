package com.login.loginpro.core.exception;

/**
 * @project: loginpro
 * @title: LoginException （默认）
 * @description：
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 20:07
 */
public class LoginException extends Exception{
    public int code;
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

    public LoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
