package com.demo.shopdemo.useroperation.mapper;

import com.demo.shopdemo.core.model.UserEntity;
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
     * 根据用户名和密码查询用户
     *
     * @param userEntity 用户实体
     * @return 用户实体
     */
    UserEntity selectUser(UserEntity userEntity);
    /**
     * 根据id查询用户
     *
     * @param id 用户id
     * @return 用户实体
     */
    UserEntity getById(Integer id);

    /**
     * 根据id删除用户
     *
     * @param id 用户id
     * @return 受影响的行数
     */
    int deleteById(Integer id);

    /**
     * 重置
     *
     * @param userEntity 用户实体
     */
    void reset(UserEntity userEntity);

    /**
     * 更新用户信息
     *
     * @param userEntity 用户实体
     * @return 受影响的行数
     */
    int update(UserEntity userEntity);

    /**
     * 封禁
     *
     * @param id 用户id
     */
    void banned(Integer id);

    /**
     * 查询所有用户
     *
     * @return 用户实体集合
     */
    List<UserEntity> selectAll();


}
