package com.login.loginpro.core.utils.send;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;

@SpringBootTest
public class SendEmailUtilTest {
    @Autowired
    private SendEmailUtil sendEmailUtil;
    @Test
    public void sendMimeMail() {
        try {
            sendEmailUtil.sendMimeMail("2648179906@qq.com", "2521415655@qq.com ","测试邮件", "测试邮件内容");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSendMimeMail() {
        try {
            sendEmailUtil.sendMimeMail("2648179906@qq.com", "测试邮件", "测试邮件内容");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}