package com.login.loginpro.login.controller;

import com.login.loginpro.core.utils.model.Resp;
import com.login.loginpro.login.model.UserRegisterTo;
import com.login.loginpro.login.service.ILoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @project: loginpro
 * @title: Register （默认）
 * @description：
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 11:48
 */
@Controller
public class RegisterController {
    private ILoginService loginService;
    public RegisterController(ILoginService loginService) {
        this.loginService = loginService;
    }
    @PostMapping("/register")
    public Resp register(@RequestBody UserRegisterTo userRegisterTo) {
        if(loginService.register(userRegisterTo)){
            return Resp.fail("注册成功");
        } else {
            return Resp.fail("注册失败");
        }
    }
}
