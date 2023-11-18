package com.login.loginpro.login.utils;

import com.login.loginpro.core.utils.send.SendEmailUtil;

import javax.mail.MessagingException;


/**
 * @project: loginpro
 * @title: SendEmailCode （默认）
 * @description：验证码工具
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 19:18
 */

public class SendCode {
    /**
     * code 为 0 代表登录验证 1 代表注册验证
     */
    public static String SendMimeMail(String to, int code) throws MessagingException {
        String subject = "";
        if (code == 0) {
            subject = "登录验证码";
        } else {
            subject = "注册验证码";
        }
        String content = "您的验证码是："+generateCode();
        return SendEmailUtil.SendMimeMail(to, subject, content);
    }


    /**
     * 生成一个随机代码。
     * 代码长度为6位，由数字组成。
     * @return 生成的随机代码。
     */
    public static String generateCode() {
        int code = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(code);
    }



}
