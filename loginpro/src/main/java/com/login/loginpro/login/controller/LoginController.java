package com.login.loginpro.login.controller;

import com.login.loginpro.core.model.UserLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @project: loginpro
 * @title: LoginController （默认）
 * @description： 登录控制器
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 19:59
 */
@Controller
public class LoginController {
    @GetMapping({"/", "/login"})
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @PostMapping("/login")
    public ModelAndView login(UserLogin userLogin) {
        ModelAndView mv = new ModelAndView("login");

        return mv;
    }
}
