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
public interface IUserInfoDao {
    /**
     *查询所有的 User 信息 ，并封装到 UserInfoModel
     * @return 返回用户信息集合
     */
    List<UserInfoModel> selectAll();
}