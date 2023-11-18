package com.login.loginpro.login.utils;

import com.login.loginpro.core.utils.SendEmailUtil;
import org.junit.Test;

import javax.mail.MessagingException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SendCodeTest {

    @Test
    public void testSendMimeMailForLoginVerification() throws MessagingException {
        // Arrange
        String to = "example@example.com";
        int code = 0;
        String expectedSubject = "登录验证码";
        String expectedContent = "您的验证码是：123456";

        SendEmailUtil sendEmailUtil = mock(SendEmailUtil.class);

        when(sendEmailUtil.SendMimeMail(to, expectedSubject, expectedContent)).thenReturn("mocked result");

        // Act
        String result = SendCode.SendMimeMail(to, code);

        // Assert
        assertEquals("mocked result", result);
        verify(sendEmailUtil).SendMimeMail(to, expectedSubject, expectedContent);
    }

    @Test
    public void testSendMimeMailForRegistrationVerification() throws MessagingException {
        // Arrange
        String to = "example@example.com";
        int code = 1;
        String expectedSubject = "注册验证码";
        String expectedContent = "您的验证码是：123456";

        SendEmailUtil sendEmailUtil = mock(SendEmailUtil.class);

        when(sendEmailUtil.SendMimeMail(to, expectedSubject, expectedContent)).thenReturn("mocked result");

        // Act
        String result = SendCode.SendMimeMail(to, code);

        // Assert
        assertEquals("mocked result", result);
        verify(sendEmailUtil).SendMimeMail(to, expectedSubject, expectedContent);
    }
}
