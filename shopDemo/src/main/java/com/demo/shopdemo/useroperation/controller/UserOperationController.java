package com.demo.shopdemo.useroperation.controller;

import com.demo.shopdemo.core.model.UserEntity;
import com.demo.shopdemo.useroperation.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户登录
 *
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 2023/11/5
 */
@Controller
public class UserOperationController{
    @Autowired
    private IUserService userService;

    /**
     * 获取用户列表
     * @return ModelAndView 对象
     */
    @GetMapping("/userlist")
    public ModelAndView userlist(){
        return new ModelAndView("/menu/userOperation/userlist");
    }

    /**
     * 编辑用户操作菜单
     * @param model 域对象
     * @param id 用户ID
     * @return ModelAndView 对象，指定视图路径 "/menu/userOperation/userlist"
     */
    @GetMapping("/usermodify")
    public ModelAndView modify(Model model, Integer id) {
        // 查询用户信息
        UserEntity byId = userService.getById(id);
        // 存到域中
        model.addAttribute("user", byId);
        //模型（Model）和会话（Session）都是用于存储信息的地方，但它们的生命周期和存在位置不同。在Web开发中，会话是服务器端的一种机制，用于记录特定客户端与服务器的一系列请求交互。它的生命周期一般是在用户关闭浏览器或者一定时间内没有活动访问时结束。而模型，更像是数据结构，用于在服务器端处理数据、验证数据、执行业务逻辑等。其生命周期通常受应用程序或请求的影响，当请求结束时，模型通常也会被销毁。
        //
        //相较于这两者，Cookies主要用于保存用户的登录状态、网站偏好设置等。它是由浏览器保存在客户端的一段数据。其生命周期可以通过设置的过期时间进行控制，如果没有设置过期时间，那么Cookie的生命周期将在浏览器关闭的时候结束。
        //
        //总的来说，模型和会话的生命周期主要取决于服务器端的处理流程和应用程序的需求，而Cookies的生命周期则主要由客户端的控制。

        return new ModelAndView("/menu/userOperation/usermodify");
    }
    @PostMapping("/usermodify")
    public ModelAndView modify(UserEntity userEntity){
        userService.modify(userEntity);
        return new ModelAndView("/menu/userOperation/userlist");
    }

    /**
     * 删除用户信息
     * @param id 用户ID
     * @return 返回删除用户后的用户列表页面
     */
    @GetMapping("/delete")
    public ModelAndView delete(Integer id) {
        // 删除用户信息
        userService.deleteById(id);
        return new ModelAndView("/menu/userOperation/userlist");
    }

    @GetMapping ("/add")
    public ModelAndView add() {
        return new ModelAndView("/menu/userOperation/useradd");
    }
    @PostMapping("/add")
    public ModelAndView save(UserEntity userEntity) {
        userService.save(userEntity);
        return new ModelAndView("/menu/userOperation/userlist");
    }
    @GetMapping ("/reset")
    public ModelAndView reset(UserEntity userEntity) {
        userService.reset(userEntity);
        return new ModelAndView("/menu/userOperation/userlist");
    }
    @GetMapping("/banned")
    public ModelAndView banned(Integer id) {
        userService.banned(id);
        return new ModelAndView("/menu/userOperation/userlist");
    }
}
