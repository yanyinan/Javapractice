package com.satrt.springweb.login.dao;

import com.satrt.springweb.core.model.entity.UserEntity;
import com.satrt.springweb.core.utils.db.DbUtilsHelper;

import java.sql.SQLException;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 17:49
 */
public class UserDao {

    /**
     *根据用户名和密码查询用户
     * @param username 用户名
     * @param password 密码
     * @return 查询到的用户. 如果没有查询到, 返回 null
     */
    public UserEntity selectByUsernameAndPassword(String username, String password) {
        String sql = "SELECT id AS id, user_name AS userName,avatar as avatar,nick_name AS nickName FROM sys_user WHERE user_name = ? AND password = ?";
        return DbUtilsHelper.queryOne(sql,UserEntity.class,username,password);
    }

    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return 查询到的用户，如果没有查询到，返回null
     */
    public UserEntity selectByUsername(String userName) {
        String sql = "select id, user_name as userName, nick_name as nickName from sys_user where username = ?";
        return DbUtilsHelper.queryOne(sql, UserEntity.class, userName);
    }

    public int insert(UserEntity userEntity) {
        String sql = "insert into sys_user(user_name, nick_name, password) values(?, ?, ?)";
        return DbUtilsHelper.update(sql,userEntity.getUserName(),userEntity.getNickName(),userEntity.getPassword());
    }
}
