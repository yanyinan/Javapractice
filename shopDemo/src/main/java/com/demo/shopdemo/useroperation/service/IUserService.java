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

    UserEntity getById(UserEntity userEntity);

    int deleteById(UserEntity userEntity);

    void reset(UserEntity userEntity);

    int save(UserEntity userEntity);

    void banned(UserEntity userEntity);

    void modify(UserEntity userEntity);

    List<UserEntity> selectAll();
}
