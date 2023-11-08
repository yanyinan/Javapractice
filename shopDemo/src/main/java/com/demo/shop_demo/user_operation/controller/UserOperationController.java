package com.demo.shop_demo.user_operation.controller;

import com.demo.shop_demo.core.model.UserEntity;
import com.demo.shop_demo.login.exception.LoginException;
import com.demo.shop_demo.user_operation.exception.UserOperationException;
import com.demo.shop_demo.user_operation.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.demo.shop_demo.core.constant.UserConstant.USER_LOGIN_MESSAGE;
import static com.demo.shop_demo.core.utils.UuidUtil.getUUID32;

/**
 * 用户操作
 *
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 2023/11/5
 */
@Controller
@RequestMapping("/userOperate")
public class UserOperationController{

    @Autowired
    private IUserService userService;

    /**
     * 获取用户列表
     * @return ModelAndView 对象
     */
    @GetMapping("/listOfUser")
    public ModelAndView userList(Model model,
                                 @RequestParam(value = "p", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "s", defaultValue = "2") int pageSize) throws UserOperationException {
        // 开启分页功能
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有用户
        List<UserEntity> userList = userService.selectAll();
        // 获取分页信息
        PageInfo<UserEntity> pageInfo = new PageInfo<>(userList);
        // 存到域中
        model.addAttribute("pageInfo",pageInfo);
        return new ModelAndView("/menu/userOperation/listOfUser");
    }

    /**
     * 编辑用户操作菜单
     * @param model 域对象
     * @param userEntity 用户
     * @return ModelAndView 对象，指定视图路径 "/menu/userOperation/listOfUser"
     */
    @GetMapping("/userModify")
    public ModelAndView userModify(Model model, UserEntity userEntity) throws LoginException, UserOperationException {
        // 查询用户信息
        UserEntity byId = userService.getById(userEntity);
        // 存到域中
        model.addAttribute("modify", byId);
        //模型（Model）和会话（Session）都是用于存储信息的地方，但它们的生命周期和存在位置不同。在Web开发中，会话是服务器端的一种机制，用于记录特定客户端与服务器的一系列请求交互。它的生命周期一般是在用户关闭浏览器或者一定时间内没有活动访问时结束。而模型，更像是数据结构，用于在服务器端处理数据、验证数据、执行业务逻辑等。其生命周期通常受应用程序或请求的影响，当请求结束时，模型通常也会被销毁。
        //
        //相较于这两者，Cookies主要用于保存用户的登录状态、网站偏好设置等。它是由浏览器保存在客户端的一段数据。其生命周期可以通过设置的过期时间进行控制，如果没有设置过期时间，那么Cookie的生命周期将在浏览器关闭的时候结束。
        //
        //总的来说，模型和会话的生命周期主要取决于服务器端的处理流程和应用程序的需求，而Cookies的生命周期则主要由客户端的控制。
        return new ModelAndView("/menu/userOperation/userModify");
    }
    @PostMapping("/userModify")
    public ModelAndView userModify(UserEntity userEntity){
        userService.modify(userEntity);
        ModelAndView modelAndView = new ModelAndView("redirect:/userOperate/listOfUser");;
        modelAndView.addObject(USER_LOGIN_MESSAGE,"修改成功");
        return modelAndView;
    }

    /**
     * 删除用户信息
     * @param userEntity 用户
     * @return 返回删除用户后的用户列表页面
     */
    @GetMapping("/userDelete")
    public ModelAndView userDelete(UserEntity userEntity) {
        // 删除用户信息
        userService.deleteById(userEntity);
        //重定向到用户界面
        return new ModelAndView("redirect:/userOperate/listOfUser");
    }

    /**
     * 处理GET请求/userAdd的映射
     * 返回userAdd页面
     */
    @GetMapping("/userAdd")
    public ModelAndView userAdd() {
        return new ModelAndView("/menu/userOperation/userAdd");
    }

    /**
     * 添加用户
     *
     * @param userEntity 用户实体
     * @return ModelAndView 返回用户列表页面
     */
    @PostMapping("/userAdd")
    public ModelAndView save(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            userEntity.setId(getUUID32(userEntity.getUsername()));
        }
        userService.save(userEntity);
        ModelAndView modelAndView = new ModelAndView("redirect:/userOperate/listOfUser");
        modelAndView.addObject(USER_LOGIN_MESSAGE, "添加成功");
        return modelAndView;
    }

    /**
     * 重置用户密码并返回重置成功提示
     *
     * @param userEntity 用户实体对象
     * @return ModelAndView对象，用于跳转到用户列表页面并传递提示信息
     */
    @GetMapping("/reset")
    public ModelAndView reset(UserEntity userEntity) {
        userService.reset(userEntity);
        ModelAndView modelAndView = new ModelAndView("redirect:/listOfUser");
        modelAndView.addObject(USER_LOGIN_MESSAGE, "重置成功");
        return modelAndView;
    }

    /**
     * 禁用用户
     * @param userEntity 用户
     * @return 重定向到用户列表页面的Maven视图对象
     */
    @GetMapping("/banned")
    public ModelAndView banned(UserEntity userEntity) {
        userService.banned(userEntity);
        ModelAndView modelAndView = new ModelAndView("redirect:/listOfUser");
        modelAndView.addObject(USER_LOGIN_MESSAGE, "用户禁用成功");
        return modelAndView;
    }

}
