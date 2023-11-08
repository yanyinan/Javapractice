package com.demo.shop_demo.user_operation.controller;

import com.demo.shop_demo.core.model.UserEntity;
import com.demo.shop_demo.user_operation.exception.UserOperationException;
import com.demo.shop_demo.user_operation.service.IUserService;
import com.demo.shop_demo.user_operation.utils.ReturnMessage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.demo.shop_demo.core.constant.UserConstant.USER_LOGIN_MESSAGE;


/**
 * 用户操作
 *
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 2023/11/5
 */
@RestController
@RequestMapping("/userOperate")
public class UserOperationController{

    @Autowired
    private IUserService userService;
    @GetMapping("/user")
    public ModelAndView user() {
        return new ModelAndView("menu/userOperation/listOfUser");
    }

    /**
     * 获取用户列表
     * @return ModelAndView 对象
     */
    @GetMapping("/listOf")
    public PageInfo userList(@RequestParam(value = "page", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "limit", defaultValue = "2") int pageSize) throws UserOperationException {
        // 开启分页功能
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有用户
        List<UserEntity> userList = userService.selectAll();
        // 获取分页信息
        PageInfo<UserEntity> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }


    /**
     * 用户修改个人信息
     *
     * @param id 用户ID（可选参数）
     * @return ModelAndView 视图对象
     */
    @GetMapping("/userModify")
    public ModelAndView userModify(@RequestParam(required = false) String id) {
        ModelAndView modelAndView = new ModelAndView("/menu/userOperation/userModify");
        if (id != null) {
            // 修改
            modelAndView.addObject("user", userService.findById(id));
        } else {
            modelAndView.addObject("user", new UserEntity());
        }
        return modelAndView;
    }

    @PostMapping("/userModify")
    public ReturnMessage userModify(UserEntity userEntity){
        try {
            userService.modify(userEntity);
            return  ReturnMessage.OperationSuccess();
        }catch (Exception e){
            return ReturnMessage.Error("更新失败，请重试");
        }
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
        return new ModelAndView("redirect:/userOperate/user");
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
        ModelAndView modelAndView = new ModelAndView("redirect:/userOperate/user");
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
        ModelAndView modelAndView = new ModelAndView("redirect:/userOperate/user");
        modelAndView.addObject(USER_LOGIN_MESSAGE, "用户禁用成功");
        return modelAndView;
    }

}
