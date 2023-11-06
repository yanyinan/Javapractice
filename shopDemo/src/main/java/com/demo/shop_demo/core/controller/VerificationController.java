//package com.demo.shopdemo.core.controller;
//
//import com.demo.shopdemo.core.model.UserEntity;
//import com.demo.shopdemo.useroperation.service.IUserService;
//import com.wf.captcha.utils.CaptchaUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 页面验证
// *
// * @author: Nanzhou
// * @version: v0.0.1
// * @Date: 2023/11/5
// */
//@Controller
//public class VerificationController {
//    @Autowired
//    private IUserService userService;
//
//    /**
//     * 获取密码验证页面
//     *
//     * @return ModelAndView对象，用于返回密码验证页面
//     */
//    @GetMapping("/passwordCertificate")
//    public ModelAndView passwordCertificate() {
//        return new ModelAndView("menu/userOperation/passwordVerificate");
//    }
//
//    /**
//     * 验证密码证书
//     * @param username 用户名
//     * @param password 密码
//     * @param captcha 验证码
//     * @param request HTTP请求对象
//     * @return ModelAndView对象，用于控制视图的转发
//     */
//    @PostMapping("/passwordCertificate")
//    public ModelAndView passwordCertificate(String username, String password, String captcha,HttpServletRequest request) {
//        ModelAndView modelAndView = new ModelAndView("redirect:/login");
//
//        // 校验验证码
//        if (captcha == null || !CaptchaUtil.ver(captcha, request)) {
//            // 验证码错误
//            // 清除验证码
//            CaptchaUtil.clear(request);
//            // 重定向到登录页面
//            return modelAndView;
//        }
//
//        // 清除验证码
//        CaptchaUtil.clear(request);
//
//        if (username != null || password != null) {
//            UserEntity u = new UserEntity();
//            u.setUsername(username);
//            u.setPassword(password);
//            userService.login(u);
//            modelAndView.setViewName("redirect:/index");
//        }
//        return modelAndView;
//    }
//}
