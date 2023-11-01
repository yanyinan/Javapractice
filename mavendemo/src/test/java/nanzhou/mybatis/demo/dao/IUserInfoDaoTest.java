package nanzhou.mybatis.demo.dao;

import nanzhou.mybatis.demo.model.UserInfoModel;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class IUserInfoDaoTest {

    private SqlSession sqlSession;

    @Before
    public void setUp() throws Exception {
        // 读取 mybatis 配置文件
        String resource = "mybatis-config.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource);) {
            // SqlSessionFactoryBuilder 对象
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            // 构建 SqlSessionFactory 对象
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);

            // 创建 SqlSession 对象
            sqlSession = sqlSessionFactory.openSession();

//            sqlSessionFactory.openSession(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void selectAll() {
        IUserInfoDao mapper = sqlSession.getMapper(IUserInfoDao.class);
        List<UserInfoModel> userInfoModel = mapper.selectAll();
        System.out.println(userInfoModel);
    }
}