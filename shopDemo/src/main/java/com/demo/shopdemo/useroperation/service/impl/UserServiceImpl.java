package com.demo.shopdemo.useroperation.service.impl;

import com.demo.shopdemo.core.model.UserEntity;
import com.demo.shopdemo.useroperation.mapper.IUserMapper;
import com.demo.shopdemo.useroperation.service.IUserService;
import com.demo.shopdemo.core.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: shopdemo
 * @description: 用户服务实现类
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 2023/11/4
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserMapper userMapper;

    @Override
    public UserEntity login(UserEntity userEntity) {
        //todo 完善null校验
        // 参数校验
        if (ObjectUtils.isEmpty(userEntity) == false && StringUtils.isEmpty(userEntity.getUsername()) == false && StringUtils.isEmpty(userEntity.getPassword()) == false) {
            return null;
        }
        // 密码加密
        userEntity.setPassword(MD5Util.encodePassword(userEntity.getPassword(), userEntity.getUsername()));

        // 查询数据库
        UserEntity user = userMapper.selectUser(userEntity);
        if ( user!= null) {
            if (user.getState() == 0 || user.getLogin()) {
                return null;
            }
            return user;
        } else {
            return null;
        }
    }

    /**
     * 根据用户ID获取用户实体对象
     *
     * @param id 用户ID
     * @return 用户实体对象
     */
    @Override
    public UserEntity getById(Integer id) {
        return userMapper.selectUser(id);
    }


    /**
     * 根据指定的id删除数据
     *
     * @param id 要删除的数据的id
     * @return 被删除的数据的数量
     */
    @Override
    public int deleteById(Integer id) {
        return userMapper.deleteById(id);
    }


    /**
     * 重置用户实体的信息
     *
     * @param userEntity 用户实体对象
     */
    @Override
    public void reset(UserEntity userEntity) {
        userMapper.reset(userEntity);
    }

    @Override
    public int save(UserEntity userEntity) {
        return userMapper.update(userEntity);
    }

    /**
     * 禁止用户
     *
     * @param id 用户ID
     */
    @Override
    public void banned(Integer id) {
        userMapper.banned(id);
    }


    /**
     * 修改用户实体对象
     *
     * @param userEntity 待修改的用户实体对象
     */
    @Override
    public void modify(UserEntity userEntity) {
        userMapper.update(userEntity);
    }


    /**
     * 重写selectAll方法
     * 从数据库中选择所有用户实体并返回列表
     * 调用userDao的selectAll方法获取所有用户实体
     * 返回获取到的所有用户实体列表
     */
    @Override
    public List<UserEntity> selectAll() {
        return userMapper.selectAll();
    }

}
