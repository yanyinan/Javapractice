package com.demo.shopdemo.login.service.impl;

import com.demo.shopdemo.core.model.UserEntity;
import com.demo.shopdemo.login.dao.IUserDao;
import com.demo.shopdemo.login.service.IUserService;
import com.demo.shopdemo.login.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登录
 *
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 2023/11/4
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;
    @Override
    public UserEntity login(UserEntity userEntity) {
        // 参数校验
        String username = userEntity.getUsername();
        String password = userEntity.getPassword();
        // 密码加密
        password = MD5Util.encodePassword(password, username);
        // 查询数据库
        return userDao.selectByUsernameAndPassword(username, password);
    }
}
