package com.example.springboot.controller;

import com.example.springboot.finalpool.UserConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/23 22:34
 */
@Controller
public class WelcomePageController {
    @RequestMapping("/welcome")
    public String index( HttpServletRequest req) {
        Object flag = req.getSession().getAttribute(UserConstant.LOGIN_USER);
        if(flag == null){
            // 没登录
            return "redirect:/login";
        }
        // 请求转发到 /WEB-INF/welcome.html 页面
        return "redirect:/WEB-INF/index.html";
    }

}
