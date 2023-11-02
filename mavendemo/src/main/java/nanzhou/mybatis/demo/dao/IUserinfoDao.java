package nanzhou.mybatis.demo.dao;

import nanzhou.mybatis.demo.model.UserInfoModel;

import java.util.List;

/**
 * @program: maven demo
 * @ClassName IUserInfoDao
 * @description:
 * @author: nanzhou
 * @create: 2023-11-01 23:13
 * @Version 1.0
 **/


public interface IUserinfoDao {

    List<UserInfoModel> selectAll(UserInfoModel model);

    int deleteById(String... ids);


    int update(UserInfoModel model);


}
