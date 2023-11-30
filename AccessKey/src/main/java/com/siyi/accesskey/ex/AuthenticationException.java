package com.siyi.accesskey.ex;

import com.siyi.accesskey.constant.ErrorCode;
import lombok.Getter;

/**
 * @author 26481
 * @description 认证异常
 * @create 2021/8/24
 */
@Getter
public class AuthenticationException extends Exception {
    private final ErrorCode errorCode;

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}

