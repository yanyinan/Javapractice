package com.satrt.springweb.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 退出业务
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 20:12
 */
@Controller
public class LogoutController {
    @RequestMapping("/logout")
    public String logout(){
        return "login/login";
    }
}
