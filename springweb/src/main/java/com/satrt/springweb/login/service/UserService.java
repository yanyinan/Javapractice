package com.satrt.springweb.login.service;

import com.satrt.springweb.core.model.entity.UserEntity;
import com.satrt.springweb.core.utils.db.MD5Util;
import com.satrt.springweb.login.dao.UserDao;
import org.springframework.util.StringUtils;

import java.sql.SQLException;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 17:03
 */
public class UserService {
    private UserDao userDao = new UserDao();


    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回 User 对象，登录失败返回 null
     */
    public UserEntity login(String username, String password) {
        // 参数校验
        if (!StringUtils.hasText(username)
                || !StringUtils.hasText(password) ) {
            return null;
        }

        // 密码加密
        password = MD5Util.encodePassword(password, username);

        // 查询数据库
        return userDao.selectByUsernameAndPassword(username, password);
    }
}
