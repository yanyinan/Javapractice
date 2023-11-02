package nanzhou.mybatis.demo.dao;

import nanzhou.mybatis.demo.model.UserInfoModel;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class IUserinfoDaoTest {

    private SqlSession sqlSession;
    private IUserinfoDao userinfoDao;

    @Before
    public void setUp() throws Exception {
        // 读取配置文件
        try(InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");){
            // 创建 SqlSessionFactoryBuilder 对象
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

            // 构建 sqlSessionFactory 对象
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
            // 创建 SqlSession 对象
            sqlSession = sqlSessionFactory.openSession();
            // 获取 IUserinfoDao 代理对象
            userinfoDao = sqlSession.getMapper(IUserinfoDao.class);
        }
    }

    @After
    public void tearDown() throws Exception {
//        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void selectAll() {
        UserInfoModel userinfoModel = new UserInfoModel();
        userinfoModel.setName("张三");
        userinfoModel.setUsername("zhangsan");

        List<UserInfoModel> userinfoModels = userinfoDao.selectAll(userinfoModel);

        for (UserInfoModel model : userinfoModels){
            System.out.println(model);
        }
    }

    @Test
    public void deleteById() {
        int rows = userinfoDao.deleteById("0b659bba-b534-4122-914f-c9b7978d0045", "34a2d2ab2ee946a49ab4a87e7f16cfb6", "7afea3df1a3b4cc5bdf1");
        System.out.println(rows);
    }
}