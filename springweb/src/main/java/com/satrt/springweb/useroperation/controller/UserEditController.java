package com.satrt.springweb.useroperation.controller;


import com.satrt.springweb.core.model.entity.UserEntity;
import com.satrt.springweb.useroperation.servise.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 用户编辑
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 20:28
 */
@Controller
@RequestMapping("/useroperate")
public class UserEditController {
    private UserService userService = new UserService();

    @RequestMapping(value = "/directory", method = RequestMethod.GET)
    public String list(Model model) {
        // templates/user/list.html
        // 查询用户列表
        List<UserEntity> list = userService.userDirectory();
        // 存到域中
        model.addAttribute("list", list);
        return "backgrounder/useroperate/userdirectory";
    }

    @RequestMapping(value = "/directory", method = RequestMethod.POST)
    public String edit(UserEntity user, RedirectAttributes redirectAttributes) {
        // 修改用户信息
        userService.update(user);
        return "backgrounder/useroperate/userdirectory";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        // 删除 id 为 1的用户，/user/delete/1
        // 删除用户信息
        if (userService.delete(id)<0){
            //抛出错误信息
        }
        return "backgrounder/useroperate/userdirectory";
    }
}
