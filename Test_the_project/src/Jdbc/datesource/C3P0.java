package Jdbc.datesource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.*;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
public class C3P0 {
    public static void main(String[] args) throws PropertyVetoException, SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/company");
        cpds.setUser("root");
        cpds.setPassword("");
        cpds.setInitialPoolSize(5);
        cpds.setMaxPoolSize(10);
        Connection connection = cpds.getConnection();
        String sql = "select * from dept";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        // 结果集 元信息。 列名，列的个数，列的类型
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()){
            for (int i = 1; i <= columnCount ; i++) {
                System.out.print(resultSet.getObject(i) + "\t");
            }
            System.out.println();
        }

        // 释放资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
