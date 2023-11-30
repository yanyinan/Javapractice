package com.siyi.accesskey.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.siyi.accesskey.constant.Constant.USER_LOGIN_STATE;

/**
 * @author 26481
 * @CreateTime: 2023-11-18  09:28
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
public class IndexController {
    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("module/index");
        //检查session中是否有用户信息
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            modelAndView.setViewName("login/login");
            return modelAndView;
        }
        return modelAndView;

    }

}
