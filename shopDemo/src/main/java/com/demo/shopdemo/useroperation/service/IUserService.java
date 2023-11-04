package com.demo.shopdemo.useroperation.service;

import com.demo.shopdemo.core.model.UserEntity;

public interface IUserService {
    /**
     * 用户登录
     * @param userEntity 用户实体
     * @return 用户实体
     */
    UserEntity login(UserEntity userEntity);
}
