package com.demo.shopdemo.login.service;

import com.demo.shopdemo.core.model.UserEntity;
import org.springframework.stereotype.Service;

/**
 * 用户登录
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 17:00
 */
@Service
public interface IUserService {
    /**
     * 用户登录
     * @param userEntity 用户实体
     * @return 用户实体
     */
    UserEntity login(UserEntity userEntity);

}
