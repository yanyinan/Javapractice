package com.demo.shopdemo.useroperation.service;

import com.demo.shopdemo.core.model.UserEntity;

import java.util.List;

public interface IUserService {
    /**
     * 用户登录
     * @param userEntity 用户实体
     * @return 用户实体
     */
    UserEntity login(UserEntity userEntity);

    UserEntity getById(Integer id);

    int deleteById(Integer id);

    void reset(UserEntity userEntity);

    int save(UserEntity userEntity);

    void banned(Integer id);

    void modify(UserEntity userEntity);

    List<UserEntity> selectAll();
}
