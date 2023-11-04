package com.demo.shopdemo.login.controller;


import com.demo.shopdemo.core.model.UserEntity;
import com.demo.shopdemo.login.service.IUserService;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录
 *
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 17:00
 */
@Controller
public class LoginController {

    @Autowired
    private IUserService userService;

    /**
     * 获取登录页面
     *
     * @return 登录页面的名称
     */
    @GetMapping(value = {"/", "/login"})
    public String loginPage() {
        return "login/login";
    }

    @PostMapping(value = {"/", "/login"})
    public String login(UserEntity userEntity, String captcha, HttpServletRequest request) {

        // 校验验证码
        if (!CaptchaUtil.ver(captcha, request)) {
            // 验证码错误
            // 清除验证码
            CaptchaUtil.clear(request);
            // 重定向到登录页面
            return "redirect:/login";
        }

        // 清除验证码
        CaptchaUtil.clear(request);

        // 校验用户名密码
        UserEntity user = userService.login(userEntity);

        // 如果用户为空，则登录失败
        if (user == null) {
            // 登录失败
            return "redirect:/login";
        }

        // 登录成功
        // 将用户信息保存到 session 中
        request.getSession().setAttribute("user", user);

        // 重定向到首页
        return "redirect:/index";
    }


}
