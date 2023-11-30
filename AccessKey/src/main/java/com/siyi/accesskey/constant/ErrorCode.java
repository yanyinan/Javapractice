package com.siyi.accesskey.constant;
import lombok.Getter;

/**
 * @author 26481
 */

@Getter
public enum ErrorCode {
    // 认证错误码
    USERNAME_PASSWORD_ERROR(4001, "用户名或密码错误"),
    CAPTCHA_ERROR(4002, "验证码错误"),
    CAPTCHA_EXPIRED(4003, "验证码已过期"),

    // 注册错误码
    USERNAME_EXIST(4010, "用户名已存在"),
    EMAIL_EXIST(4011, "邮箱已存在"),
    PHONE_EXIST(4012, "手机号已存在"),

    // 用户信息错误
    USER_NOT_FOUND(4100, "用户不存在"),
    USER_DISABLED(4101, "用户被禁用"),
    EMAIL_NOT_EXIST(4102, "邮箱不存在"),
    PHONE_NOT_EXIST(4103, "手机号不存在"),
    ACCOUNT_NOT_FOUND(4104,"账户不存在"),


    // 权限错误码
    ACCESS_DENIED(4030, "访问被拒绝"),

    // 通用错误码
    UNKNOWN_ERROR(5000, "未知错误"),
    DATABASE_ERROR(5001, "数据库操作错误"),
    EXTERNAL_API_ERROR(5002, "外部接口调用错误"),

    // 具体业务错误码
    EMPTY_USERNAME_PASSWORD(6000, "用户名或密码为空"),
    EMPTY_PHONE(6001, "手机号不能为空"),
    EMPTY_EMAIL(6002, "邮箱不能为空"),
    EMAIL_NOT_ACTIVATED(6003, "您的邮箱未激活请先点击链接进行激活"),
    ACTIVATION_CODE_NOT_EXIST(6004, "激活码不存在"),
    EMAIL_SEND_FAIL(6005, "邮件发送失败"),

    //账号被锁定,请第二天再试
    ACCOUNT_LOCKED(6006,"账号被锁定,请第二天再试");


    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
