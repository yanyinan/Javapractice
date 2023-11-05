package com.demo.shopdemo.useroperation.dao;

import com.demo.shopdemo.core.model.UserEntity;

import java.util.List;

public interface IUserDao {
    /**
     * 根据用户名和密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户实体
     */
    UserEntity selectByUsernameAndPassword(String username, String password);

    UserEntity getById(Integer id);

    int deleteById(Integer id);

    void reset(UserEntity userEntity);

    int update(UserEntity userEntity);

    void banned(Integer id);

    List<UserEntity> selectAll();
}
