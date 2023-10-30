package com.satrt.springweb.useroperation.controller;

import com.satrt.springweb.core.constant.Constant;
import com.satrt.springweb.core.model.entity.UserEntity;
import com.satrt.springweb.exception.login.LoginException;
import com.satrt.springweb.exception.sql.SqlServiceException;
import com.satrt.springweb.useroperation.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/29 23:39
 */
@Controller
@RequestMapping("/Password")
public class PasswordVerificationController {
    private UserService userService;
    PasswordVerificationController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/Verification")
    public String Verification(){
        return "backgrounder/userOperation/passwordVerification";
    }
    @PostMapping("/Verification")
    public String Verification(String password, @SessionAttribute(Constant.LOGIN_USER) UserEntity user, HttpServletRequest request) throws SqlServiceException, LoginException {
        String userName = user.getUserName();
        UserEntity userEntity = userService.login(userName,password);
        if (userEntity != null){
            request.getSession().setAttribute("verification", "ok");
        }else {
            return "redirect:/logout";
        }
        return "backgrounder/index";
    }
}
