package nanzhou.mybatis.demo.dao;

import nanzhou.mybatis.demo.model.UserInfoModel;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

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
            //自动提交
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

    @Test
    public void insert() {
        IUserInfoDao mapper = sqlSession.getMapper(IUserInfoDao.class);

        UserInfoModel model = new UserInfoModel();
        model.setId(UUID.randomUUID().toString());
        model.setUsername("test");
        model.setPassword("123456");
        model.setName("测试");
        model.setGender(1);
        model.setBirthday(new java.util.Date());
        model.setPhone("12345678901");
        model.setEmail("123@qq.com");
        model.setCreateDate(new java.util.Date());
        model.setDel(false);

        int rows = mapper.insert(model);


        System.out.println(rows);
    }
    @After
    public void tearDown() throws Exception {
        // 提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void delete() {
        IUserInfoDao mapper = sqlSession.getMapper(IUserInfoDao.class);
        int rows = mapper.delete("7afea3df1a3b4cc5bdfcd57bfee62046");
        System.out.println(rows);
    }
}