package com.login.loginpro.login.controller;

import com.login.loginpro.core.model.UserLogin;
import com.login.loginpro.login.service.ILoginService;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    /**
     * ioc 构造器注入
     */
    private ILoginService loginService;
    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping({"/", "/login"})
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @PostMapping("/login")
    public ModelAndView login(UserLogin userLogin, String captcha, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:login");
        // 校验验证码
        if (!CaptchaUtil.ver(captcha, request)) {
            // 验证码错误
            // 清除验证码
            CaptchaUtil.clear(request);
            
        }
        // 清除验证码
        CaptchaUtil.clear(request);
        if(userLogin.getLid() != null){
            if (userLogin.getPassword() != null){

            }
        }

        return mv;
    }
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }
}
