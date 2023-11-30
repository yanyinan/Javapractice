package com.siyi.accesskey.controller.ex;

import com.siyi.accesskey.constant.ErrorCode;
import com.siyi.accesskey.ex.AuthenticationException;
import com.siyi.accesskey.utils.Resp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @author 26481
 * @CreateTime: 2023-11-16  23:46
 * @Description: TODO
 * @Version: 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理认证异常
     *
     * @param e 认证异常
     * @return Resp
     * 错误码：
     * 4001：用户名或密码错误
     * 4002：验证码错误
     * 4003：验证码已过期
     * 4004：用户名或密码为空
     * 4005：手机号不能为空
     * 4006：邮箱不能为空
     * 4007：您的邮箱未激活请先点击链接进行激活
     * 4008：激活码不存在
     * 4009：邮件发送失败
     * 4010：用户名已存在
     * 4011：邮箱已存在
     * 4012：手机号已存在
     * 4013：未知错误
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public Resp handleAuthenticationException(AuthenticationException e) {
        ErrorCode errorCode = e.getErrorCode();
        return switch (errorCode) {
            case USERNAME_PASSWORD_ERROR -> Resp.error("用户名或密码错误!");
            case CAPTCHA_ERROR -> Resp.error("验证码错误!");
            case CAPTCHA_EXPIRED -> Resp.error("验证码已过期!");
            case USERNAME_EXIST -> Resp.error("用户名已存在!");
            case EMAIL_EXIST -> Resp.error("邮箱已存在!");
            case PHONE_EXIST -> Resp.error("手机号已存在!");
            case USER_NOT_FOUND -> Resp.error("用户不存在!");
            case USER_DISABLED -> Resp.error("用户被禁用!");
            case ACCESS_DENIED -> Resp.error("访问被拒绝!");
            case DATABASE_ERROR -> Resp.error("数据库操作错误!");
            case EXTERNAL_API_ERROR -> Resp.error("外部接口调用错误!");
            case EMPTY_USERNAME_PASSWORD -> Resp.error("用户名或密码为空!");
            case EMPTY_PHONE -> Resp.error("手机号不能为空!");
            case EMPTY_EMAIL -> Resp.error("邮箱不能为空!");
            case EMAIL_NOT_ACTIVATED -> Resp.error("您的邮箱未激活请先点击链接进行激活!");
            case ACTIVATION_CODE_NOT_EXIST -> Resp.error("激活码不存在!");
            case EMAIL_SEND_FAIL -> Resp.error("邮件发送失败!");
            case ACCOUNT_NOT_FOUND -> Resp.error("账户不存在!");
            case EMAIL_NOT_EXIST -> Resp.error("邮箱不存在!");
            case PHONE_NOT_EXIST -> Resp.error("手机号不存在");
            case ACCOUNT_LOCKED->Resp.error("账号被锁定,请第二天再试");
            default -> Resp.error("未知错误!");
        };
    }
}
