package com.demo.shopdemo.login.dao;

import com.demo.shopdemo.core.model.UserEntity;
public interface IUserDao {
    /**
     * 根据用户名和密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户实体
     */
    UserEntity selectByUsernameAndPassword(String username, String password);
}
