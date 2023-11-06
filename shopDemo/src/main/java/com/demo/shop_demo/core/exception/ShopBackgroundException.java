package com.demo.shop_demo.core.exception;

/**
 * @project: shopDemo
 * @title: ShopBackgroundException
 * @description: 商品后台异常
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 2023/11/6
 */
public class ShopBackgroundException extends Exception{
    public ShopBackgroundException() {
        super();
    }

    public ShopBackgroundException(String message) {
        super(message);
    }

    public ShopBackgroundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShopBackgroundException(Throwable cause) {
        super(cause);
    }

    public ShopBackgroundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
