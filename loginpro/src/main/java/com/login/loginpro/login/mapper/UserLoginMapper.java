package com.login.loginpro.login.mapper;

import com.login.loginpro.core.model.UserLogin;

/**
* @author 26481
* @description 针对表【user_login(用户登录信息表)】的数据库操作Mapper
* @createDate 2023-11-14 19:51:53
* @Entity core.model.UserLogin
*/
public interface UserLoginMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(UserLogin record);

    UserLogin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserLogin record);

    int updateByPrimaryKey(UserLogin record);

}
