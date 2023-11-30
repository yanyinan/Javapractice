package com.siyi.accesskey.constant;

/**
 * @CreateTime: 2023-11-11  18:49
 * @Description: TODO
 * @Version: 1.0
 */
public interface Constant {
    /**
     * 用户登录状态,储存loginUser对象
     */
    String USER_LOGIN_STATE = "userLoginState";
    String USER_MODIFY_STATE = "userModifyState";

    String CAPTCHA = "captcha";
    /**
    用于model传递消息message
     */
    String MESSAGE = "message";
    /**
     * 黑名单ip前缀
     */
    String USER_IP = "Ip_";
    /**
     * 登录错误的封禁一天
     */
    String LOGIN_IP = "loginIp_";
    /**
     * 自动登录退出的token
     */
    String TOKEN_KEY = "token";

    String FORGET_KEY = "forgetKey_";
    String FORGET_VALUE_PHONE = "phone";
    String FORGET_VALUE_EMAIL = "email";
    /**
     * 是否需要验证码
     */
    String SHOW_CAPTCHA = "showCaptcha";
    /**
     * 记住密码
     */
    String REMEMBER_ME = "remember_me";

    /**
     * 登录错误的黑名单
     */
    String LOGIN_BLACK_LIST = "loginBlacklist";
    String USER_BLACK_LIST_IP = "userBlacklistIp";
    /**
     * 手机验证码模板
     */
    String SMS_TEMPLATE = "{\"code\":\"{{code}}\"}";
    String SMS_REPLACE = "{{code}}";


}
