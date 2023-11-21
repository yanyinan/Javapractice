package com.login.loginpro.login.service.impl;

import com.login.loginpro.core.model.UserLogin;
import com.login.loginpro.login.model.UserLoginTo;
import com.login.loginpro.login.mapper.UserLoginMapper;
import com.login.loginpro.login.model.UserRegisterTo;
import com.login.loginpro.login.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @project: loginpro
 * @title: LoginServiceImpl （默认）
 * @description：
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 20:05
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    UserLoginMapper userLoginMapper;
    /**
     * 登录
     *
     * @param userLoginTo
     * @return UserLogin
     */
    @Override
    public Boolean uselogin(UserLoginTo userLoginTo) {
        UserLogin userLogin = new UserLogin();
        //判断对象是否为空
        if (userLoginTo != null){
            //密码登录
            if (userLoginTo.getLid()!= null && userLoginTo.getPassword()!= null){
                String lid = userLoginTo.getLid();
                if (isValidPhoneNumber(lid)){
                    userLogin.setPhone(Integer.parseInt(lid));
                }else if (isValidEmail(lid)){
                    userLogin.setEmail(lid);
                }else {
                    userLogin.setLid(lid);
                }
                userLogin.setPassword(userLoginTo.getPassword());
                //查询用户是否存在
                if (userLoginMapper.selectBy(userLogin).size() == 1){
                    //返回成功
                    return true;
                }
            }
            //邮箱验证登录
            if (userLoginTo.getEmail() != null){
                userLogin.setEmail(userLoginTo.getEmail());
            }
            //手机验证登录
            if (userLoginTo.getPhone()!= null){
                userLogin.setPhone(userLoginTo.getPhone());
            }
            //查询用户是否存在
            if (userLoginMapper.selectBy(userLogin).size() == 1){
                //返回成功
                return true;
            }
        }
        return false;
    }

    @Override
    public UserLogin getUserLogin(UserLoginTo userLoginTo) {
        return null;
    }

    @Override
    public Boolean register(UserRegisterTo userRegisterTo) {
        if (userRegisterTo!= null){
            UserLogin userLogin = new UserLogin();
            if (userRegisterTo.getLid()!= null ){
                userLogin.setLid(userRegisterTo.getLid());
            }
            if ( userRegisterTo.getEmail()!= null){
                userLogin.setEmail(userRegisterTo.getEmail());
            }
            if (userRegisterTo.getPhone()!= null){
                userLogin.setPhone(userRegisterTo.getPhone());
            }
            // 判断用户是否存在
            if (userLoginMapper.selectBy(userLogin).size() == 0){
                userLogin.setPassword(userRegisterTo.getPassword());
                //插入用户
                userLoginMapper.insertSelective(userLogin);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<UserLogin> selectByMsg(UserLoginTo userLoginto) {
        if (userLoginto!= null){
            UserLogin userLogin = new UserLogin();
            if (userLoginto.getLid()!= null ){
                userLogin.setLid(userLoginto.getLid());
            }
            if ( userLoginto.getEmail()!= null){
                userLogin.setEmail(userLoginto.getEmail());
            }
            if (userLoginto.getPhone()!= null){
                userLogin.setPhone(userLoginto.getPhone());
            }
            return userLoginMapper.selectBy(userLogin);
        }else {
            return null;
        }
    }

    /**
     * 邮箱验证
     * @param email   邮箱
     * @return boolean
     */
    public static boolean isValidEmail(String email) {
        // 定义邮箱正则表达式
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(emailRegex);

        // 创建 matcher 对象
        Matcher matcher = pattern.matcher(email);

        // 判断是否匹配
        return matcher.matches();
    }
    /**
     * 手机号验证
     * @param phoneNumber 手机号
     * @return boolean
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // 定义手机号正则表达式
        // 注意：这个正则表达式是一个简化版本，实际项目中可能需要根据具体需求调整
        String phoneRegex = "^[1-9]\\d{9}$";

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(phoneRegex);

        // 创建 matcher 对象
        Matcher matcher = pattern.matcher(phoneNumber);

        // 判断是否匹配
        return matcher.matches();
    }
}
