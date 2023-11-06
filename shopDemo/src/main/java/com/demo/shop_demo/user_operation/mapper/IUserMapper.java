package com.demo.shop_demo.user_operation.mapper;

import com.demo.shop_demo.core.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: shopdemo
 * @description: 用户mapper
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 2023/11/4
 */
@Mapper
public interface IUserMapper {

    /**
     * 根据字段查询用户
     *
     * @param userEntity 用户实体
     * @return 用户实体集合
     */
    List<UserEntity> selectUser(UserEntity userEntity);

    /**
     * 根据字段删除用户
     *
     * @param userEntity 用户实体
     * @return 受影响的行数
     */
    int deleteUser(UserEntity userEntity);

    /**
     * 更新用户信息
     *
     * @param userEntity 用户实体
     * @return 受影响的行数
     */
    int update(UserEntity userEntity);

    /**
     * 插入用户信息
     *
     * @param userEntity 用户实体
     * @return 受影响的行数
     */
    int insert(UserEntity userEntity);
}
