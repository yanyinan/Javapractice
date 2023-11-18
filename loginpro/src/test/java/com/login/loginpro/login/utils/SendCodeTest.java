package com.login.loginpro.login.utils;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@SpringBootTest
public class SendCodeTest {

    @Autowired
    private JavaMailSender javaMailSender;
    @Test
    public void testSendMimeMail() throws MessagingException {
        System.out.println(javaMailSender);
        //创建一封mime邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //创建mime邮件的辅助类对象，将mime设置为muitipart类型
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //设置邮件标题和内容
        helper.setSubject("开发喵用户激活邮件");
        helper.setText("<a href='http://0.0.0.0:80/active?sid=xxxxxx'>点击这里激活开发喵账号</a>");
        //设置发件人和收件人
        helper.setTo("2648179906@qq.com");
        helper.setFrom("2521415655@qq.com");
        //发送邮件
        javaMailSender.send(mimeMessage);
    }
}
