package com.demo.shop_demo.user_operation.service.impl;

import com.demo.shop_demo.core.model.UserEntity;
import com.demo.shop_demo.core.utils.UuidUtil;
import com.demo.shop_demo.login.exception.LoginException;
import com.demo.shop_demo.user_operation.exception.UserOperationException;
import com.demo.shop_demo.user_operation.mapper.IUserMapper;
import com.demo.shop_demo.user_operation.service.IUserService;
import com.demo.shop_demo.core.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.demo.shop_demo.core.constant.LoginConstant.*;

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

    /**
     * 登录
     * @param userEntity 用户实体
     * @return 用户实体
     * @throws LoginException
     */
    @Override
    public UserEntity login(UserEntity userEntity) throws LoginException {
        // 参数校验
        if (userEntity.getUsername() == null || userEntity.getPassword() == null) {
            throw new LoginException("用户名或密码不能为空");
        }
        // 密码加密
        userEntity.setPassword(MD5Util.encodePassword(userEntity.getPassword(), userEntity.getUsername()));

        // 查询数据库
        List<UserEntity> user = userMapper.selectUser(userEntity);
        if (user.size() == 1) {
            if ( user.get(0).getState() == null ) {
                throw new LoginException(LOGIN_BANED);
            }
            //todo 是否登录验证
//            System.out.println(user.get(0).getLogin());
//            if (user.get(0).getLogin() == 0) {
//                throw new LoginException(LOGIN_SUCCESS);
//            }
            return user.get(0);
        } else {
            throw new LoginException(LOGIN_ERROR_USER_NOT_EXIST);
        }
    }

    /**
     * 根据用户ID获取用户实体对象
     *
     * @param userEntity 用户实体对象
     * @return 用户实体对象
     * @throws UserOperationException
     */
    @Override
    public UserEntity getById(UserEntity userEntity) throws UserOperationException {
        List<UserEntity> user = userMapper.selectUser(userEntity);
        if (user.size() == 1) {
            return user.get(0);
        } else {
            throw new UserOperationException(LOGIN_ERROR_USER_NOT_EXIST);
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
        userMapper.updateByPrimaryKey(userEntity);
    }

    /**
     * 保存用户实体对象
     *
     * @param userEntity 需要保存的用户实体对象
     * @return 更新的行数
     */
    @Override
    public int save(UserEntity userEntity) {
        userEntity.setPassword(MD5Util.encodePassword(userEntity.getPassword(), userEntity.getUsername()));
        return userMapper.insert(userEntity);
    }

    /**
     * 禁用用户
     *
     * @param userEntity 需要禁用的用户实体对象
     */
    @Override
    public void banned(UserEntity userEntity) {
        userEntity.setState(0);
        userMapper.updateByPrimaryKey(userEntity);
    }

    /**
     * 修改用户实体对象
     *
     * @param userEntity 待修改的用户实体对象
     */
    @Override
    public void modify(UserEntity userEntity) {

//        if (ObjectUtils.isEmpty(userEntity)){
//            throw new ServiceException("用户不能为空");
//        }
        int row = 0;
        if (!StringUtils.hasText(userEntity.getId())){
            // 新增
            userEntity.setId(UuidUtil.getUUID32());
            // 密码加密
            String password = MD5Util.encodePassword(userEntity.getPassword(), userEntity.getUsername());
            userEntity.setPassword(password);
            // 数据库用户名做了唯一约束，所以此处省略验证用户名是否存在
            row = userMapper.insert(userEntity);
        }else {
            row = userMapper.updateByPrimaryKey(userEntity);
        }
//        if (row != 1){
////            throw new ServiceException("更新失败");
//        }
    }

    /**
     * 重写 selectUser 方法
     *
     * @return 用户实体列表
     * @throws UserOperationException
     */
    @Override
    public List<UserEntity> selectAll() throws UserOperationException {

        List<UserEntity> userEntityList = userMapper.selectUser(null);
        if (userEntityList.size() > 0) {
            return userEntityList;
        } else {
            throw new UserOperationException(LOGIN_ERROR_USER_NOT_EXIST);
        }
    }

    @Override
    public Object findById(String id) {
        return null;
    }

}
