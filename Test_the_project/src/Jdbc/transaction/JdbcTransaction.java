package Jdbc.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
public class JdbcTransaction {

    static {
        // 注册驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    String user ="root";
    String pass = "";
    String url = "jdbc:mysql://localhost:3306/jdbctest";
    Connection conn;
    PreparedStatement preparedStatement;

    public Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }
    public void colse() throws SQLException {
        if (conn != null){
            conn.close();
        }
        if (preparedStatement != null){
            preparedStatement.close();
        }
    }
    public void update(String sql,Object... params){
        try {
            getConnection();
            //开启事务
            conn.setAutoCommit(false);

            preparedStatement = conn.prepareStatement(sql);
            setParams(params);
            preparedStatement.executeUpdate(sql);

            //提交事务
            conn.commit();
        }catch (Exception e) {
            e.printStackTrace();
            try {
                //回滚事务
                conn.rollback();
                System.out.println("事务提交失败");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void setParams(Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i+1,params[i]);
        }
    }

}
