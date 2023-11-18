package com.login.loginpro.core.utils.send;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
public class SendEmailUtilTest {


    @Test
    public void sendMimeMail() {
        try {
            SendEmailUtil.SendMimeMail("2648179906@qq.com", "测试邮件", "测试邮件内容");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}