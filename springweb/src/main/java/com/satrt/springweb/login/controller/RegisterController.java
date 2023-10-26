package com.satrt.springweb.login.controller;

import com.satrt.springweb.exception.login.RegisterException;
import com.satrt.springweb.core.model.entity.UserEntity;
import com.satrt.springweb.useroperation.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户注册
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 19:09
 */
@Controller
public class RegisterController {
    private UserService userService = new UserService();

    @RequestMapping("/register")
    public String Register(UserEntity userEntity, String Registration_password, String re_Registration_password) throws RegisterException {
        //获取注册密码与确认密码
        String password = Registration_password;
        String rePassword = re_Registration_password;
        // 注册
        userService.register(userEntity, password,rePassword);
        // 注册成功
        return "redirect:/login";
    }

}
