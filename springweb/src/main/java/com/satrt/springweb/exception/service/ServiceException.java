package com.satrt.springweb.exception.service;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 20:40
 */
public class ServiceException extends Exception{
    private Integer code;

    public ServiceException(String message) {
        this(message,3);
    }

    public ServiceException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }
}
