package com.siyi.accesskey.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siyi.accesskey.constant.ErrorCode;
import com.siyi.accesskey.ex.AuthenticationException;
import com.siyi.accesskey.mapper.LoginUserMapper;
import com.siyi.accesskey.model.domain.LoginUser;
import com.siyi.accesskey.service.UserService;
import com.siyi.accesskey.utils.MD5Util;
import com.siyi.accesskey.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: SiYi
 * @CreateTime: 2023-11-10  22:22
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser>
        implements UserService {
    @Autowired
    private LoginUserMapper loginUserMapper;
    @Autowired
    private UserBlacklistService userBlacklistService;

    /**
     * 用户名密码登录
     *
     * @param loginUser 用户
     * @return 登录成功返回用户信息，否则返回null
     */
    @Override
    public LoginUser login(LoginUser loginUser) throws AuthenticationException {
        if (loginUser != null && !StringUtils.isAnyBlank(loginUser.getUsername(), loginUser.getPassword())) {
            String username = loginUser.getUsername();
            String encodePassword = MD5Util.getEncodePassword(loginUser);
            LoginUser user = loginUserMapper.selectOne(new QueryWrapper<LoginUser>().eq("username", username));
            //判断用户名是否存在
            if (user == null) {
                throw new AuthenticationException(ErrorCode.USERNAME_PASSWORD_ERROR);
            }
            //判断Redis中是否存在该用户的黑名单
            if (userBlacklistService.isUserInBlacklist(user.getUserId())) {
                throw new AuthenticationException(ErrorCode.USER_DISABLED);
            }
            //用户名存在,校验密码
            if (!user.getPassword().equals(encodePassword)) {
                //密码错误,将Redis中错误次数+1
                return null;
            }
            //密码正确,校验状态
            if (user.getUserStatus() == 0) {
                throw new AuthenticationException(ErrorCode.USER_DISABLED);
            }
            //是否被删除,1为删除,0为未删除
            if (user.getIsDelete() == 1) {
                throw new AuthenticationException(ErrorCode.ACCOUNT_NOT_FOUND);
            }
            //校验完毕,返回用户信息
            return user;
        }
        log.error("用户名或密码为空");
        return null;
    }


    /**
     * 用户名密码注册
     *
     * @param loginUser 用户
     * @return 注册成功返回true，否则返回false
     */

    @Override
    public boolean registerByUsername(LoginUser loginUser) {
        // 判断用户名和密码是否为空
        if (loginUser != null && !StringUtils.isAnyBlank(loginUser.getUsername(), loginUser.getPassword())) {
            // Controller层已经进行过重复校验,因此直接可以进行注册
            String id = UUIDUtil.uuid32();
            loginUser.setUserId(id);
            String encodePassword = MD5Util.getEncodePassword(loginUser);
            loginUser.setPassword(encodePassword);
            return save(loginUser);
        }
        log.error("用户名或密码为空");
        return false;
    }

    @Override
    public boolean registerByEmail(LoginUser loginUser) {
        if (loginUser != null && StringUtils.isNotBlank(loginUser.getEmail())) {
            String id = UUIDUtil.uuid32();
            loginUser.setUserId(id);
            //密码加密
            String encodePassword = MD5Util.getEncodePassword(loginUser);
            loginUser.setPassword(encodePassword);
            return save(loginUser);
        }
        return false;
    }

    @Override
    public boolean registerByPhone(LoginUser loginUser) {
        if (loginUser != null && StringUtils.isNotBlank(loginUser.getPhone())) {
            String id = UUIDUtil.uuid32();
            loginUser.setUserId(id);
            //密码加密
            String encodePassword = MD5Util.getEncodePassword(loginUser);
            loginUser.setPassword(encodePassword);
            return save(loginUser);
        }
        return false;
    }

    /**
     * 登录检查用户名是否存在
     *
     * @param loginUser 用户
     */
    @Override
    public void checkUsername(LoginUser loginUser) throws AuthenticationException {
        // 判断用户名是否为空
        if (loginUser != null && StringUtils.isNotBlank(loginUser.getUsername())) {
            String username = loginUser.getUsername();
            LoginUser result = loginUserMapper.selectOne(new QueryWrapper<LoginUser>().eq("username", username));
            //如果存在则返回true
            if (result != null) {
                // 如果用户名已存在，抛出用户名已存在异常
                throw new AuthenticationException(ErrorCode.USERNAME_EXIST);
            }
        } else {
            // 如果用户名为空，可以抛出异常或返回特定的错误码
            throw new AuthenticationException(ErrorCode.EMPTY_USERNAME_PASSWORD);
        }

    }


    /**
     * 登录检查邮箱是否存在
     *
     * @param loginUser 用户
     */
    public LoginUser emailExist(LoginUser loginUser) {
        // 判断邮箱是否为空
        if (loginUser != null && StringUtils.isNotBlank(loginUser.getEmail())) {
            String email = loginUser.getEmail();
            return loginUserMapper.selectOne(new QueryWrapper<LoginUser>().eq("email", email));
        }
        log.error("邮箱为空");
        return null;


    }

    @Override
    public LoginUser phoneExist(LoginUser loginUser) {
        // 判断手机号是否为空
        if (loginUser != null && StringUtils.isNotBlank(loginUser.getPhone())) {
            String phone = loginUser.getPhone();
            return loginUserMapper.selectOne(new QueryWrapper<LoginUser>().eq("phone", phone));
        }
        log.error("手机号为空");
        return null;
    }


    @Override
    public LoginUser phoneStatus(LoginUser loginUser) throws AuthenticationException {
        LoginUser user = phoneExist(loginUser);

        if (user == null) {
            throw new AuthenticationException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        if (user.getUserStatus() != 1) {
            throw new AuthenticationException(ErrorCode.USER_DISABLED);
        }
        if (user.getIsDelete() == 1) {
            throw new AuthenticationException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        return user;

    }

    @Override
    public LoginUser emailStatus(LoginUser loginUser) throws AuthenticationException {
        LoginUser user = emailExist(loginUser);
        if (user == null) {
            throw new AuthenticationException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        if (user.getUserStatus() != 1) {
            throw new AuthenticationException(ErrorCode.USER_DISABLED);
        }
        if (user.getIsDelete() == 1) {
            throw new AuthenticationException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        return user;
    }

    @Override
    public LoginUser modify(LoginUser loginUser) throws AuthenticationException {
        if (loginUser == null) {
            throw new AuthenticationException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        if (loginUser.getUserStatus() == 0) {
            throw new AuthenticationException(ErrorCode.USER_DISABLED);
        }
        if (loginUser.getIsDelete() == 1) {
            throw new AuthenticationException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        //密码加密
        String encodePassword = MD5Util.getEncodePassword(loginUser);
        loginUser.setPassword(encodePassword);
        //修改数据库中的密码
        int i = loginUserMapper.updateById(loginUser);
        if (i == 0) {
            throw new AuthenticationException(ErrorCode.DATABASE_ERROR);
        }
        return loginUser;
    }

    /**
     * 登录测试
     *
     * @param ip 用户id
     */
    @Override
    public void handleLoginAttempt(String ip) throws AuthenticationException {
        int errorCount = userBlacklistService.addToBlacklistAndGetErrorCount(ip);
        // 如果错误次数大于等于5，则将用户所属ip加入黑名单
        if (errorCount >= 5) {
            userBlacklistService.addToBlacklist(ip);
            throw new AuthenticationException(ErrorCode.ACCOUNT_LOCKED);
        }
    }
}
