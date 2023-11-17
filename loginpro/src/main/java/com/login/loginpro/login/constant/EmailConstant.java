package com.login.loginpro.login.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @project: loginpro
 * @title: EmailConstant （默认）
 * @description：
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 19:28
 */
@Component
public class EmailConstant {
    @Value("${spring.mail.username}")
    public static final String MAIL_FROM = null;

}
