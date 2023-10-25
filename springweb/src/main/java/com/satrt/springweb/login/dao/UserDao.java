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
     * @throws SQLException
     */
    public UserEntity selectByUsernameAndPassword(String username, String password) {
        String sql = "SELECT id AS id, user_name AS userName,avatar as avatar,nick_name AS nickName FROM sys_user WHERE user_name = ? AND password = ?";
        return DbUtilsHelper.queryOne(sql,UserEntity.class,username,password);
    }
}
