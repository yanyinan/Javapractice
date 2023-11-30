package com.siyi.accesskey.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author siyi
 */
@Component
public class EmailSenderUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    public void sendActivationEmail(String toEmail,String sib) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);


        helper.setSubject("激活邮件");
        String activationLink = "http://" + serverAddress + ":" + serverPort + "/register/email?sib=" + sib;
        String content = "<a href='" + activationLink + "'>点击这里激活【AccessKey】账号</a>";
        helper.setText(content, true);
        helper.setTo(toEmail);
        helper.setFrom(fromEmail);
        javaMailSender.send(mimeMessage);

    }
    public void sendVerificationCodeEmail(String toEmail, String verificationCode) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setSubject("验证码邮件");
        String content = "您的验证码是：" + verificationCode;
        helper.setText(content, true);
        helper.setTo(toEmail);
        helper.setFrom(fromEmail);

        javaMailSender.send(mimeMessage);
    }


    /**
     * 生成激活码
     *
     * @param email 邮箱地址
     * @return 加密的sib激活码
     */
    public String generateSib(String email) {
        String salt = reverseString(email);
        //通过当前时间戳与邮箱得到加密后的激活sib
        return MD5Util.getEncodePassword(email,salt);
    }
    //翻转字符串
    private static String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}
