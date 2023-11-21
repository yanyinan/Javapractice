package com.login.loginpro.login.controller;

import com.login.loginpro.core.utils.model.RespUrl;
import com.login.loginpro.login.model.UserLoginTo;
import com.login.loginpro.core.utils.model.Resp;
import com.login.loginpro.login.service.ILoginService;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    @ResponseBody
    public Resp login(@RequestBody UserLoginTo userLoginTo, HttpServletRequest request) {
        if ( userLoginTo.getCaptcha()!= null && !CaptchaUtil.ver(userLoginTo.getCaptcha(), request)) {
            // 验证码错误
            // 清除验证码
            CaptchaUtil.clear(request);
            // 重定向到登录页面
            return Resp.fail("验证码错误");
        }
        if (loginService.uselogin(userLoginTo)) {
            return Resp.ok(new RespUrl("/index","登录成功"));
        }else {
            return Resp.fail("用户名或密码错误");
        }
    }
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }
    @GetMapping("/register")
    public String register() {
        return "/register";
    }
    @GetMapping("/index")
    public String index() {
        return "/index";
    }
}
