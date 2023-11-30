package com.siyi.accesskey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.siyi.accesskey.ex.AuthenticationException;
import com.siyi.accesskey.model.domain.LoginUser;

/**
 * @Author: SiYi
 * @CreateTime: 2023-11-10  22:17
 * @Description: TODO
 * @Version: 1.0
 */
public interface UserService extends IService<LoginUser> {
    /**
     * 用户名登录
     *
     * @param loginUser 用户
     * @return 用户
     */
    LoginUser login(LoginUser loginUser) throws AuthenticationException;


    /**
     * 用户名注册
     *
     * @param loginUser 用户
     * @return 如果注册成功返回true，否则返回false
     */
    boolean registerByUsername(LoginUser loginUser);

    /**
     * 邮箱注册
     *
     * @param loginUser 用户
     * @return 如果注册成功返回true，否则返回false
     */
    boolean registerByEmail(LoginUser loginUser);

    /**
     * 手机号注册
     *
     * @param loginUser 用户
     * @return 如果注册成功返回true，否则返回false
     */
    boolean registerByPhone(LoginUser loginUser);

    /**
     * 验证用户名是否存在
     *
     * @param loginUser 用户
     */
    void checkUsername(LoginUser loginUser) throws AuthenticationException;


    /**
     * 验证邮箱是否存在,
     *
     * @param loginUser 用户
     */
    LoginUser emailExist(LoginUser loginUser) ;
    /**
     * 验证手机号是否存在
     *
     * @param loginUser 用户
     */
    LoginUser phoneExist(LoginUser loginUser) ;

    /**
     * 验证手机号账户状态
     *
     * @param loginUser 用户
     */
    LoginUser phoneStatus(LoginUser loginUser) throws AuthenticationException;
    /**
     * 验证邮箱账户状态
     *
     * @param loginUser 用户
     */
    LoginUser emailStatus(LoginUser loginUser) throws AuthenticationException;
    /**
     * 修改密码
     *
     * @param loginUser 用户
     */
    LoginUser modify(LoginUser loginUser) throws AuthenticationException;

     void handleLoginAttempt(String ip) throws AuthenticationException;
}
