package com.login.loginpro.login.controller;


import com.login.loginpro.core.utils.model.Resp;
import com.login.loginpro.login.model.UserLoginTo;
import com.login.loginpro.login.service.ILoginService;
import com.login.loginpro.login.utils.SendCode;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    private SendCode sendCode;
    private ILoginService loginService;

    public CodeController(ILoginService loginService) {
        this.loginService = loginService;
    }
    @PostMapping ("/getCode")
    public Resp getCode(@RequestParam String email) throws MessagingException {
        int code = 0;
//        if (userLoginto != null && loginService.selectByMsg(userLoginto) != null) {
//            if (userLoginto.getPhone() != null) {
//                sendCode.SendMimeMail(userLoginto.getEmail(),0);
//            } else if (userLoginto.getEmail() != null) {
//                sendCode.SendMimeMail(userLoginto.getEmail(),0);
//            }
//        }
        if (email!= null) {
            sendCode.SendMimeMail(email,code);
        }
        return null;
    }
}
