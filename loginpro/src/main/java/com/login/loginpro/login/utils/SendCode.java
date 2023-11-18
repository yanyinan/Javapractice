package com.login.loginpro.login.utils;

import com.login.loginpro.core.utils.SendEmailUtil;
import lombok.Setter;

import javax.mail.MessagingException;


/**
 * @project: loginpro
 * @title: SendEmailCode （默认）
 * @description：验证码工具
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 19:18
 */

public class SendCode  {
    /**
     * code 为 0 代表登录验证 1 代表注册验证
     */
    public static String SendMimeMail(String to,int code) throws MessagingException {
        String subject = "";
        if (code == 0) {
            subject = "登录验证码";
        } else {
            subject = "注册验证码";
        }
        String content = "您的验证码是：123456";
        return SendEmailUtil.SendMimeMail(to, subject, content);
    }
    public static void main(String[] args) throws MessagingException {
        SendCode.SendMimeMail("2648179906@qq.com", 0);
    }

}
