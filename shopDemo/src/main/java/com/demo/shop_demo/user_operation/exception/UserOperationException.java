package com.demo.shop_demo.user_operation.exception;

import com.demo.shop_demo.core.exception.ShopBackgroundException;

/**
 * @project: shopDemo
 * @title: UserOperationException （默认）
 * @description：
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 2023/11/6 14:37
 */
public class UserOperationException extends ShopBackgroundException {
    public UserOperationException() {
    }

    public UserOperationException(String message) {
        super(message);
    }

    public UserOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserOperationException(Throwable cause) {
        super(cause);
    }

    public UserOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
