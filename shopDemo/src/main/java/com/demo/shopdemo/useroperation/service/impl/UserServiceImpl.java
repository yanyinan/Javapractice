package com.demo.shopdemo.useroperation.service.impl;

import com.demo.shopdemo.core.model.UserEntity;
import com.demo.shopdemo.useroperation.dao.IUserDao;
import com.demo.shopdemo.useroperation.service.IUserService;
import com.demo.shopdemo.core.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public UserEntity getById(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return userDao.deleteById(id);
    }

    @Override
    public void reset(UserEntity userEntity) {
        userDao.reset(userEntity);
    }

    @Override
    public int save(UserEntity userEntity) {
        return userDao.update(userEntity);
    }

    @Override
    public void banned(Integer id) {
        userDao.banned(id);
    }

    @Override
    public void modify(UserEntity userEntity) {
        userDao.update(userEntity);
    }

    @Override
    public List<UserEntity> selectAll() {
        return userDao.selectAll();
    }
}
