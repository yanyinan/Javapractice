package com.login.loginpro.core.utils.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.login.loginpro.login.constant.EmailConstant.MAIL_FROM;

/**
 * @project: loginpro
 * @title: SendEmailUtil （默认）
 * @description：
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 20:21
 */
@Component
public class SendEmailUtil {

    @Autowired
    private  JavaMailSender javaMailSender;
    public void sendMimeMail(String to, String from, String subject, String content) throws MessagingException {
        System.out.println(javaMailSender);
        //创建一封mime邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //创建mime邮件的辅助类对象，将mime设置为muitipart类型
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //设置邮件标题和内容
        helper.setSubject(subject);
        helper.setText(content);
        //设置发件人和收件人
        helper.setTo(to);
        helper.setFrom(from);
        //发送邮件
        javaMailSender.send(mimeMessage);
    }
    public void sendMimeMail(String to,String subject,String content) throws MessagingException {
        System.out.println(MAIL_FROM);
        sendMimeMail(to,MAIL_FROM,subject,content);
    }

}
