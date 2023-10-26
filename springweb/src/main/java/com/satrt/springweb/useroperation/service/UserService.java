package com.satrt.springweb.useroperation.service;

import com.satrt.springweb.core.exception.login.LoginException;
import com.satrt.springweb.core.exception.login.RegisterException;
import com.satrt.springweb.core.model.entity.UserEntity;
import com.satrt.springweb.core.utils.db.MD5Util;
import com.satrt.springweb.useroperation.dao.UserDao;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户业务
 *
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 17:03
 */
public class UserService {
    private UserDao userDao = new UserDao();
    
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回 User 对象，登录失败返回 null
     */
    public UserEntity login(String username, String password) throws LoginException {
        // 参数校验
        if (!StringUtils.hasText(username)
                || !StringUtils.hasText(password)) {
            throw new LoginException("参数校验失败", 0);
        }
        // 密码加密
        password = MD5Util.encodePassword(password, username);
        // 查询数据库
        return userDao.selectByUsernameAndPassword(username, password);
    }

    /**
     * 用户注册
     *
     * @param userEntity
     * @param password
     * @param rePassword
     * @return
     * @throws RegisterException
     */
    public void register(UserEntity userEntity, String password, String rePassword) throws RegisterException {
        //参数校验
//        if (!StringUtils.isEmpty(userEntity)
//                || !StringUtils.hasText(password)
//                || !StringUtils.hasText(rePassword)) {
//            throw new RegisterException("参数校验失败", 0);
//        }
        //密码校验
        if (!password.equals(rePassword)) {
            throw new RegisterException("密码重复", 0);
        }

        // 用户名是否存在
        UserEntity entity = userDao.selectByUsername(userEntity.getUserName());
        if (entity != null) {
            throw new RegisterException("用户已存在", 1);
        }
        // 密码加密
        String encodePassword = MD5Util.encodePassword(password, userEntity.getUserName());

        userEntity.setPassword(encodePassword);

        // 插入数据
        int rows = userDao.insert(userEntity);

        if (rows != 1) {
            throw new RegisterException("未知错误", 3);
        }
    }

    /**
     * 用户列表
     * @return 返回用户列表
     */
    public List<UserEntity> userDirectory() {
        return userDao.selectAllUser();
    }

    /**
     * 修改用户信息
     * @param user 修改用户
     */
    public void update(UserEntity user) {
        StringBuilder sqlParams = null;
        List params = new ArrayList();
        if (user.getAvatar() != null){
            sqlParams.append("avatar = ?, ");
            params.add(user.getAvatar());
        }
        if (user.getUserName() != null){
            sqlParams.append("username = ?, ");
            params.add(user.getUserType());
        }
        if (user.getNickName() != null){
            sqlParams.append("nick_name = ?, ");
            params.add(user.getNickName());
        }
        if (user.getPassword() != null){
            sqlParams.append("password = ?, ");
            params.add(user.getPassword());
        }
        if (user.getStatus() != null){
            sqlParams.append("status = ?, ");
            params.add(user.getStatus());
        }
        if (user.getEmail() != null){
            sqlParams.append("email = ?, ");
            params.add(user.getEmail());
        }
        if (user.getPhoneNumber() != null){
            sqlParams.append("phonenumber = ?, ");
            params.add(user.getPassword());
        }
        if (user.getSex() != null){
            sqlParams.append("sex = ?, ");
            params.add(user.getSex());
        }
        if (user.getUserType() != null){
            sqlParams.append("user_type = ?, ");
            params.add(user.getUserType());
        }
        // 最后会多一个 , 需要去掉
        sqlParams.deleteCharAt(sqlParams.length() - 1);

        userDao.update(sqlParams,user.getId());
    }

    /**
     * 根据用户Id删除用户
     * @param id 用户id
     */
    public int delete(Integer id) {
        return userDao.delete(id);
    }

    /**
     * 根据Id查找用户
     * @param id 用户Id
     * @return 返回用户
     */
    public UserEntity getById(Integer id) {
        return userDao.selectById(id);
    }
}
