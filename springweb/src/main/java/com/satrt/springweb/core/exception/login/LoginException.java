package com.satrt.springweb.core.exception.login;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 19:38
 */
public class LoginException extends Exception{
    private Integer code;
    public LoginException() {
        this("参数校验失败",0);
    }

    public LoginException(String message) {
        this(message,3);
    }

    public LoginException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public LoginException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }
}
