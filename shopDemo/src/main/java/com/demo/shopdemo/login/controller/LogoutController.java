package com.demo.shopdemo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


/**
 * 退出业务
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 20:12
 */
@Controller
public class LogoutController {
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        //清楚session
        session.invalidate();
        return "login/login";
    }
}
