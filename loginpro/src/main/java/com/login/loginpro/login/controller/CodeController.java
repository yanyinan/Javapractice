package com.login.loginpro.login.controller;

import com.login.loginpro.core.model.UserLogin;
import com.login.loginpro.core.utils.model.Resp;
import com.login.loginpro.login.utils.SendCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.MessagingException;

/**
 * @project: loginpro
 * @title: CodeControlle （默认）
 * @description：
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 16:43
 */
@Controller
public class CodeController {
    @GetMapping("/getCode")
    public Resp getCode(UserLogin userLogin){
        if (userLogin.getPhone() != null){
            try {
                SendCode.SendMimeMail(userLogin.getEmail(),1);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
        if (userLogin.getEmail() !=null){
            try {
                SendCode.SendMimeMail(userLogin.getEmail(),1);
                return Resp.ok("邮箱验证码发送成功");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
}
