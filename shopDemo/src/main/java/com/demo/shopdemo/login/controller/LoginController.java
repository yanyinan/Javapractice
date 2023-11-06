package com.demo.shopdemo.login.controller;

import com.demo.shopdemo.core.model.UserEntity;
import com.demo.shopdemo.useroperation.service.IUserService;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.demo.shopdemo.core.constant.LoginConstant.*;

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
     * 处理GET请求，对应的URL路径为"/"或"/login"
     *
     * 返回一个包含登录页面路径的ModelAndView对象
     */
    @GetMapping({"/", "/login"})
    public ModelAndView login(){
        return new ModelAndView("/login/login");
    }

    /**
     * 登录方法
     *
     * @param model       模型对象
     * @param userEntity  用户实体对象
     * @param captcha     验证码
     * @param request     请求对象
     * @return 登录结果
     */
    @PostMapping(value = {"/", "/login"})
    public ModelAndView login(UserEntity userEntity, String captcha,Model model, HttpServletRequest request,HttpServletResponse response) {
        // 校验验证码
        if (captcha == null || !CaptchaUtil.ver(captcha, request)) {
            // 验证码错误
            // 清除验证码
            CaptchaUtil.clear(request);
            // 重定向到登录页面
            return new ModelAndView("redirect:login");
        }
        // 清除验证码
        CaptchaUtil.clear(request);
        // 校验用户名密码
        UserEntity user = userService.login(userEntity);
        // 如果用户为空，则登录失败
        if (user != null) {
            // 登录成功
            // 将用户信息保存到 model 中
            model.addAttribute(LOGIN_USER_MESSAGE, user);
            // 保存登录状态保存到 cookie 中
//            response.addCookie(new Cookie(LOGIN_STATUS, LOGIN_SUCCESS));
            return new ModelAndView("menu/index");
        }
        return new ModelAndView("redirect:login");
    }
    /**
     * 生成验证码
     *
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @throws Exception 如果生成验证码过程中发生异常
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }

    /**
     * 显示主页菜单的请求处理方法
     *
     * @return 主页菜单页面的名称
     */
    @RequestMapping("/index")
    public String index() {
        return "menu/index";
    }

    /**
     * 登出
     * @param session session对象
     * @return 登出成功后跳转的页面
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        //清除session
        session.invalidate();
        return "login/login";
    }
}
