package Jdbc.procedure;

import java.sql.*;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
public class ProcedureDemo01 {
    public static void main(String[] args) {

        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //建立连接
            Connection connection  = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbctest","root","");
            //定义 SQL 语句
            String sql = "{CALL proc_adder(?,?,?)}";
            //预编译 SQL
            CallableStatement callableStatement = connection.prepareCall(sql);
            //设置输入值
            callableStatement.setInt(1,1);
            callableStatement.setInt(2,2);
            //设置输出值
            callableStatement.registerOutParameter(3,Types.INTEGER);
            //执行操作
            callableStatement.executeUpdate();
            //获取结果
            int n = callableStatement.getInt(3);

            System.out.println(n);
            //关闭资源
            callableStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
