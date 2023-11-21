package com.login.loginpro.login.utils;



import com.login.loginpro.core.utils.send.SendEmailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@SpringBootTest
public class SendCodeTest {
    @Autowired
    private SendCode sendCode;
    @Test
    public void testSendMimeMailForLoginVerification() throws MessagingException {
        String to = "2648179906@qq.com";
        int code = 0;
        sendCode.SendMimeMail(to, code);

    }

    @Test
    public void testSendMimeMailForRegistrationVerification() throws MessagingException {
        String to = "test@example.com";
        int code = 1;

        sendCode.SendMimeMail(to, code);

    }
}
