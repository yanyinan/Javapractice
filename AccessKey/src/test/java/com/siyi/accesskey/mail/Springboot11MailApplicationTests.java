package com.siyi.accesskey.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author: SiYi
 * @CreateTime: 2023-11-15  19:05
 * @Description: TODO
 * @Version: 1.0
 */
@SpringBootTest
public class Springboot11MailApplicationTests {
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Test
    void testSendMimeMail() throws MessagingException {
        //创建一封mime邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //创建mime邮件的辅助类对象，将mime设置为muitipart类型
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //设置邮件标题和内容
        helper.setSubject("开发喵用户激活邮件");
        String content = "<a href='http://127.0.0.1:8080/emailVertification?sid=xxxxxx'>点击这里激活开发喵账号</a>";
        helper.setText("<a href='http://0.0.0.0:80/active?sid=xxxxxx'>点击这里激活开发喵账号</a>");
        //设置发件人和收件人
        helper.setTo("505831532@qq.com");
        helper.setFrom("505831532@qq.com");
        //发送邮件
        javaMailSender.send(mimeMessage);

    }
}
