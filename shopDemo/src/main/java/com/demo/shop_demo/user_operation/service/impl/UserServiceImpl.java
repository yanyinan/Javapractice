package com.demo.shop_demo.user_operation.service.impl;

import com.demo.shop_demo.core.model.UserEntity;
import com.demo.shop_demo.user_operation.mapper.IUserMapper;
import com.demo.shop_demo.user_operation.service.IUserService;
import com.demo.shop_demo.core.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: shop_demo
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
        List<UserEntity> user = userMapper.selectUser(userEntity);
        if (user.size() == 1) {
            if (user.get(0).getState() == 0 || user.get(0).getLogin()) {
                return null;
            }
            return user.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据用户ID获取用户实体对象
     *
     * @param userEntity 用户实体对象
     * @return 用户实体对象
     */
    @Override
    public UserEntity getById(UserEntity userEntity) {
        List<UserEntity> user = userMapper.selectUser(userEntity);
        if (user.size() == 1) {
            if (user.get(0).getState() == 0 || user.get(0).getLogin()) {
                return null;
            }
            return user.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据用户ID删除用户实体对象
     *
     * @param userEntity 用户实体对象
     * @return 用户实体对象
     */

    @Override
    public int deleteById(UserEntity userEntity) {
        return userMapper.deleteUser(userEntity);
    }

    /**
     * 重置用户实体的信息
     *
     * @param userEntity 用户实体对象
     */
    @Override
    public void reset(UserEntity userEntity) {
        userMapper.update(userEntity);
    }

    /**
     * 保存用户实体对象
     *
     * @param userEntity 需要保存的用户实体对象
     * @return 更新的行数
     */
    @Override
    public int save(UserEntity userEntity) {
        return userMapper.insert(userEntity);
    }

    /**
     * 禁用用户
     *
     * @param userEntity 需要禁用的用户实体对象
     */
    @Override
    public void banned(UserEntity userEntity) {
        userMapper.update(userEntity);
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
     * 重写 selectUser 方法
     * 从数据库中选择所有用户实体并返回列表
     * 调用userDao的 selectUser 方法获取所有用户实体
     * 返回获取到的所有用户实体列表
     */
    @Override
    public List<UserEntity> selectAll() {
        List<UserEntity> userEntityList = userMapper.selectUser(new UserEntity());
        if (userEntityList.size() > 0) {
            return userEntityList;
        } else {
            return null;
        }
    }

}
