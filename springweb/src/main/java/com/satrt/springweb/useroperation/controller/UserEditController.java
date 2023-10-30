package com.satrt.springweb.useroperation.controller;

import com.satrt.springweb.core.constant.Constant;
import com.satrt.springweb.core.model.entity.UserEntity;
import com.satrt.springweb.exception.login.RegisterException;
import com.satrt.springweb.exception.sql.SqlServiceException;
import com.satrt.springweb.useroperation.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 用户编辑
 *
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 20:28
 */
@Controller
@RequestMapping("/userOperate")
public class UserEditController {
    private UserService userService;
    UserEditController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userDirectory")
    public String list(Model model, @SessionAttribute(Constant.LOGIN_USER) UserEntity user) throws SqlServiceException {
        String userFlag = "普通用户";
        if (!userFlag.equals(user.getUserType())){
            return "backgrounder/info/info";
        }
        // 查询用户列表
        List<UserEntity> userList = userService.userDirectory();
        // 存到域中
        model.addAttribute("userList", userList);
        return "/backgrounder/userOperation/userDirectory";
    }

    @GetMapping ("/edit")
    public String edit(Model model, Integer id) throws SqlServiceException {
        // 查询用户信息
        UserEntity byId = userService.getById(id);
        // 存到域中
        model.addAttribute("user", byId);

        return "backgrounder/userOperation/leader/userEdit";
    }

    @PostMapping("/edit")
    public String edit(UserEntity user, @RequestParam("phonenumber") String phoneNumber ,HttpServletRequest request) throws SqlServiceException {
        // 修改用户信息
        user.setPhoneNumber(phoneNumber);
        userService.update(user);
        request.getSession().setAttribute("verification", null);
        return "redirect:/userOperate/userDirectory";
    }
    @GetMapping ("/userAdd")
    public String addUser(Model model, Integer id) throws SqlServiceException {
        // 查询用户信息
        UserEntity byId = userService.getById(id);
        // 存到域中
        model.addAttribute("user", byId);
        return "backgrounder/userOperation/leader/userAdd";
    }
    @PostMapping("/userAdd")
    public String addUser(UserEntity userEntity, String Registration_password, String re_Registration_password,HttpServletRequest request) throws RegisterException, SqlServiceException {
        //获取注册密码与确认密码
        String password = Registration_password;
        String rePassword = re_Registration_password;
        // 注册
        userService.register(userEntity, password,rePassword);
        request.getSession().setAttribute("verification", null);
        return "redirect:/userOperate/userDirectory";
    }
    @GetMapping ( "/delete")
    public String delete(Integer id,HttpServletRequest request) throws SqlServiceException {
        // 删除用户信息
        userService.delete(id);
        request.getSession().setAttribute("verification", null);
        return "redirect:/userOperate/userDirectory";
    }
    @GetMapping("/avatarEdit")
    public String avatarEdit(){
        return "backgrounder/userOperation/leader/avatarEdit";
    }
    @PostMapping("/avatarEdit")
    public String avatarEdit(@RequestParam("avatar") MultipartFile avatar,HttpServletRequest request, @SessionAttribute(Constant.LOGIN_USER) UserEntity user) throws SqlServiceException, IOException {
        if (!avatar.isEmpty()){
            userService.updateAvatar(avatar,user);
        }
        request.getSession().setAttribute("verification", null);
        return "redirect:/LoginUserMsg";
    }
}
