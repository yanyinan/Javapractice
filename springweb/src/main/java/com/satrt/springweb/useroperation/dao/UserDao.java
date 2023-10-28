package com.satrt.springweb.useroperation.dao;

import com.satrt.springweb.exception.sql.SqlServiceException;
import com.satrt.springweb.core.model.entity.UserEntity;
import com.satrt.springweb.core.utils.db.DbUtilsHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * user 数据交互
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 17:49
 */
@Repository
public class UserDao {

    /**
     *根据用户名和密码查询用户
     * @param username 用户名
     * @param password 密码
     * @return 查询到的用户. 如果没有查询到, 返回 null
     */
    public UserEntity selectByUsernameAndPassword(String username, String password) throws SqlServiceException {
        String sql = "SELECT id AS id, user_name AS userName,avatar as avatar,nick_name AS nickName,status,email,sex,user_type as userType,create_by as createBy,create_time as createTime,update_by as updateBy,update_time as updateTime FROM sys_user WHERE user_name = ? AND password = ?";
        return DbUtilsHelper.queryOne(sql,UserEntity.class,username,password);
    }

    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return 查询到的用户，如果没有查询到，返回null
     */
    public UserEntity selectByUsername(String userName) throws SqlServiceException {
        String sql = "select id, user_name as userName, nick_name as nickName from sys_user where user_name = ?";
        return DbUtilsHelper.queryOne(sql, UserEntity.class, userName);
    }

    /**
     * 用户注册
     * @param userEntity 用户
     * @return 返回修改行数
     */
    public int insert(UserEntity userEntity) throws SqlServiceException {
        String sql = "insert into sys_user(user_name, nick_name, password) values(?, ?, ?)";
        return DbUtilsHelper.update(sql,userEntity.getUserName(),userEntity.getNickName(),userEntity.getPassword());
    }

    /**
     * 查询所用用户信息
     * @return 返回用户信息集合
     */
    public List<UserEntity> selectAllUser() throws SqlServiceException {
        String sql = "SELECT id AS id, user_name AS userName,avatar as avatar,nick_name AS nickName,status as status, email as email,phonenumber as phoneNumber,sex as sex,user_type as userType,create_by as createBY,create_time as createTime,update_by as updateBy,update_time as updateTime,del_flag as delFlag FROM sys_user";
        return DbUtilsHelper.queryList(sql,UserEntity.class);
    }

    /**
     * 更新用户信息
     *
     * @param sqlParams 用户信息
     * @param params
     */
    public void update(StringBuilder sqlParams, List params) throws SqlServiceException {
        StringBuilder sql = new StringBuilder("update sys_user set ");
        sql.append(sqlParams);
        System.out.println(sql);
        DbUtilsHelper.update(String.valueOf(sql),params.toArray());
    }

    /**
     * 删除用户信息
     * @param id 用户id
     * @return 返回更改行数
     */
    public int delete(Integer id) throws SqlServiceException {
        String sql = "delete  from sys_user where id = ?";
        return DbUtilsHelper.update(sql,id);
    }

    /**
     * 根据用户 id 查找用户信息
     * @param id 用户id
     * @return 返回用户实体
     */
    public UserEntity selectById(Integer id) throws SqlServiceException {
        String sql = "SELECT id AS id, user_name AS userName,avatar as avatar,nick_name AS nickName,status as status, email as email,phonenumber as phoneNumber,sex as sex,user_type as userType,create_by as createBY,create_time as createTime,update_by as updateBy,update_time as updateTime,del_flag as delFlag from sys_user where id = ?";
        return DbUtilsHelper.queryOne(sql,UserEntity.class,id);
    }
}
