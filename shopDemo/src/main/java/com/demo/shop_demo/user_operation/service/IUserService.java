package com.demo.shop_demo.user_operation.service;

import com.demo.shop_demo.core.model.UserEntity;
import com.demo.shop_demo.login.exception.LoginException;
import com.demo.shop_demo.user_operation.exception.UserOperationException;

import java.util.List;
/**
 * @program: shop-demo
 * @description: 用户服务接口
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 2023/11/5
 */

public interface IUserService {
    /**
     * 用户登录
     * @param userEntity 用户实体
     * @return 用户实体
     */
    UserEntity login(UserEntity userEntity) throws LoginException;
    /**
     * 根据id查询用户
     * @param userEntity 用户实体
     * @return 用户实体
     */
    UserEntity getById(UserEntity userEntity) throws LoginException, UserOperationException;
    /**
     * 根据字段删除用户
     * @param userEntity 用户实体
     * @return 影响行数
     */
    int deleteById(UserEntity userEntity);
    /**
     * 重置密码
     * @param userEntity 用户实体
     */
    void reset(UserEntity userEntity);
    /**
     * 保存用户
     * @param userEntity 用户实体
     * @return 影响行数
     */
    int save(UserEntity userEntity);
    /**
     * 禁用用户
     * @param userEntity 用户实体
     */
    void banned(UserEntity userEntity);
    /**
     * 修改用户
     * @param userEntity 用户实体
     */
    void modify(UserEntity userEntity);
    /**
     * 查询所有用户
     * @return 用户实体集合
     */
    List<UserEntity> selectAll() throws UserOperationException;
}
