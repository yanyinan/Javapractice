package com.login.loginpro.login.service;

import com.login.loginpro.core.model.UserLogin;
import com.login.loginpro.login.model.UserLoginTo;
import com.login.loginpro.login.model.UserRegisterTo;

/**
 * @project: loginpro
 * @title: ILoginService （默认）
 * @description： 登录业务接口
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 2023/11/14
 */
public interface ILoginService {
    Boolean uselogin(UserLoginTo userLoginTo);
    UserLogin getUserLogin(UserLoginTo userLoginTo);

    Boolean register(UserRegisterTo userRegisterTo);
}
