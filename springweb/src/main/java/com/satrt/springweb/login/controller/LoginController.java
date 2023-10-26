package com.satrt.springweb.login.controller;

import com.satrt.springweb.core.constant.Constant;
import com.satrt.springweb.core.exception.login.LoginException;
import com.satrt.springweb.core.model.entity.UserEntity;
import com.satrt.springweb.useroperation.service.UserService;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户登录
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 17:00
 */
@Controller
public class LoginController {
    private UserService userService = new UserService();

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String loginPage() {
        return "login/login";
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.POST)
    public String login(@RequestParam("name") String username, @RequestParam("password") String password, String captcha, HttpServletRequest request, Model model) throws LoginException {

//         校验验证码
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
        UserEntity user = userService.login(username, password);

        if (user == null) {
            // 登录失败
            return "redirect:/login";
        }
        // 登录成功
        // 将用户信息保存到 session 中
        request.getSession().setAttribute(Constant.LOGIN_USER, user);
        // 重定向到首页
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "backgrounder/index";
    }
}
