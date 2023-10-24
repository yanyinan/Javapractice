package com.example.springboot.controller;

import com.example.springboot.entity.daoentity.KfmUser;
import com.example.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/23 23:43
 */
@Controller
public class RegisterController {
    private UserService userService = new UserService();
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(HttpServletRequest req){
        KfmUser kfmUser = new KfmUser();
        // 获取请求数据
        kfmUser.setName(req.getParameter("nickname"));
        kfmUser.setUsername(req.getParameter("rename"));
        kfmUser.setBirth(Date.valueOf(req.getParameter("birth")));
        kfmUser.setCreateTime(Date.valueOf(LocalDate.now()));
        kfmUser.setPhone(req.getParameter("phone"));
        kfmUser.setEmail(req.getParameter("email"));
        String password = req.getParameter("Registration-password");
        String repassword = req.getParameter("re-Registration-password");
        if (password.equals(repassword)) {
            kfmUser.setPassword(password);
        } else {
            //todo 提示密码错误
            System.out.println("密码输入不一致");
            return "redirect:/register";
        }

        Boolean registerUser = userService.registerUser(kfmUser);
        if (registerUser){
            System.out.println("用户注册成功，即将返回登录页面");
            return "redirect:/login";
        }else {
            //todo 提示创建失败
            System.out.println("用户注册失败");
            return "redirect:/register";
        }
    }
}
