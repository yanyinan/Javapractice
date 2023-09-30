package test0932.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
public class SqlUtils {
    static {
        // 注册驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户连接信息
     */
    private String user;
    private String pass;
    private String url;
    private Connection conn;
    private PreparedStatement prestate;
    private ResultSet resultSet;

    /**
     * 有参构造
     *
     * @param user 用户名
     * @param pass 用户密码
     * @param url  数据库链接
     */
    public SqlUtils(String user, String pass, String url) {
        this.user = user;
        this.pass = pass;
        this.url = url;
    }

    /**
     * 与数据库建立连接
     *
     * @return 链接
     * @throws SQLException
     */
    public Connection conn() throws SQLException {
        conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }

    /**
     * 关闭连接 conn prestate
     *
     * @throws SQLException
     */
    public void colse() throws SQLException {
        if (conn != null) {
            conn.close();
        }
        if (prestate != null) {
            prestate.close();
        }
    }

    /**
     * 初始化表操作
     *
     * @param sql sql语句
     * @throws SQLException
     */
    public void initialize(String sql) throws SQLException {
        conn();
        prestate = conn.prepareStatement(sql);
        prestate.execute();
    }


    /**
     * 查询单个
     *
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public <T> T selectOne(String sql, Resultmappable<T> resultmapper, Object... params) throws SQLException {
        conn();
        prestate = conn.prepareStatement(sql);
        setParams(params);
        // 执行操作
        resultSet = prestate.executeQuery();
        // 解析结果
        if (resultSet.next()) {
            return resultmapper.mapper(resultSet);
        }

        return null;
    }
    public <T> T selectOne(String sql, Class<T> tClass, Object... params) throws SQLException {
        conn();
        prestate = conn.prepareStatement(sql);
        setParams(params);
        // 执行操作
        resultSet = prestate.executeQuery();
        // 解析结果
        if (resultSet.next()) {
            return mapper(resultSet,tClass);
        }

        return null;
    }

    private <T> T mapper(ResultSet resultSet, Class<T> tClass) {
        try {
            //获取构造
            Constructor<T> constructor = tClass.getDeclaredConstructor();
            //设置权限
            constructor.setAccessible(true);
            //创建对象
            T t = constructor.newInstance();

            //解析结果集
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取列数
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                //获取列名
                String columnLabel = metaData.getColumnLabel(i);

                //处理列名 小驼峰命名
                if (columnLabel.contains("_")){
                    columnLabel = toCamelCase(columnLabel);
                }

                try{
                    //获取方法
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnLabel,tClass);
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    //设置权限
                    writeMethod.setAccessible(true);

                    //获取结果值
                    Object values = resultSet.getObject(i);
                    writeMethod.invoke(t, values);
                } catch (Exception e){
                    // 没有对应的属性
                    System.err.println("没有对应的属性："+columnLabel+"跳过");
                }

            }
            return t;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理列名 小驼峰命名
     *
     * @param columnLabel
     * @return
     */
    private  String toCamelCase(String columnLabel) {
        String[] split = columnLabel.split("_");
        StringBuffer stringBuffer = new StringBuffer(split[0].toLowerCase());
        for (int i = 1; i < split.length; i++) {
            stringBuffer.append(split[i].substring(0,1).toUpperCase()).append(split[i].substring(1).toLowerCase());
        }
        return stringBuffer.toString();
    }



    /**
     * 查询多个
     *
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public <T> List<T> selectAll(String sql, Resultmappable<T> resultmapper, Object... params) throws SQLException {
        conn();
        prestate = conn.prepareStatement(sql);
        setParams(params);

        // 执行操作
        resultSet = prestate.executeQuery();

        // 解析结果
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            T t = resultmapper.mapper(resultSet);
            list.add(t);
        }
        return list;
    }
    /**
     * 查询多个
     *
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public <T> List<T> selectAll(String sql, Class<T> tClass, Object... params) throws SQLException {
        conn();
        prestate = conn.prepareStatement(sql);
        setParams(params);

        // 执行操作
        resultSet = prestate.executeQuery();

        // 解析结果
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            T t = mapper(resultSet,tClass);
            list.add(t);
        }
        return list;
    }
    /**
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public int insert(String sql, Object... params) throws SQLException {
        grud(sql, Statement.RETURN_GENERATED_KEYS, params);
        resultSet = prestate.getGeneratedKeys();
        return resultSet.next() ? resultSet.getInt(1) : -1;
    }

    /**
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public int delete(String sql, Object... params) throws SQLException {
        return grud(sql, Statement.NO_GENERATED_KEYS, params);
    }

    /**
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public int update(String sql, Object... params) throws SQLException {
        return grud(sql, Statement.NO_GENERATED_KEYS, params);
    }

    /**
     * GRUD操作
     *
     * @param sql
     * @param params
     */
    public int grud(String sql, int autoGeneratedKeys, Object... params) throws SQLException {
        conn();
        prestate = conn.prepareStatement(sql, autoGeneratedKeys);
        setParams(params);
        return prestate.executeUpdate();
    }

    /**
     * 设置值
     *
     * @param params 元素
     * @throws SQLException
     */
    private void setParams(Object... params) throws SQLException {
        if (params != null) {
            // 设置参数
            for (int i = 0; i < params.length; i++) {
                int j = i + 1;
                prestate.setObject(j, params[i]);
            }
        }
    }

    public int selectId(String sql) throws SQLException {
        conn();

//        prestate = conn.prepareStatement(sql);
        // 执行操作
        Statement statement = conn.createStatement();
        int n = statement.executeQuery(sql).getInt("quantity");
        statement.close();
//        return prestate.executeQuery().getInt("quantity");
        return n;

    }
}