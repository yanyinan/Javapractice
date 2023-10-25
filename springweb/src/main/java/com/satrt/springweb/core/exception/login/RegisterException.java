package com.satrt.springweb.core.exception.login;

import lombok.Getter;

/**
 * 注册异常处理
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 14:52
 */
@Getter
public class RegisterException extends Exception{
    /**
     * 0: 参数校验失败
     * 1: 用户名已存在
     * 2: 密码不一致
     * 3: 未知错误
     */
    private Integer code;
    public RegisterException() {
        this("参数校验失败",0);
    }

    public RegisterException(String message) {
        this(message,3);
    }

    public RegisterException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public RegisterException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }
}
