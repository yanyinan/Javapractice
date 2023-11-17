package com.login.loginpro.login.service.impl;

import com.login.loginpro.core.model.UserLogin;
import com.login.loginpro.core.utils.model.UserLoginTo;
import com.login.loginpro.login.service.ILoginService;
import org.springframework.stereotype.Service;

/**
 * @project: loginpro
 * @title: LoginServiceImpl （默认）
 * @description：
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 20:05
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Override
    public UserLogin uselogin(UserLoginTo userLoginTo) {
        //邮箱验证登录
        if (userLoginTo.getEmail() != null){

        }
        //手机验证登录
        if (userLoginTo.getPhone()!= null){

        }
        //密码登录
        if (userLoginTo.getLid()!= null && userLoginTo.getPassword()!= null){

        }
        return null;
    }
}
