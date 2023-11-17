package com.login.loginpro.login.service;

import com.login.loginpro.core.model.UserLogin;
import com.login.loginpro.core.utils.model.UserLoginTo;

import java.util.List;

/**
 * @project: loginpro
 * @title: ILoginService （默认）
 * @description： 登录业务接口
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 2023/11/14
 */
public interface ILoginService {
    List<UserLogin> uselogin(UserLoginTo userLoginTo);
}
