package com.satrt.springweb.core.utils.reader;



import com.satrt.springweb.core.utils.model.BasicDataModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取基本数据源 存储在 basicData 与 serverDataSource
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/21 10:25
 */
public class ConfigReader {
    /**
     * 数据库配置
     */
    public static BasicDataModel basicData;
    /**
     * 服务器配置
     */

    static {
        try {// 通过 ClassLoader 获取资源文件的输入流
            ClassLoader classLoader = ConfigReader.class.getClassLoader();
            InputStream dbinputStream = classLoader.getResourceAsStream("BasicDataSource.properties");

            BasicDataModel basicDataSource = new BasicDataModel();
            if (dbinputStream != null) {
                Properties properties = new Properties();
                properties.load(dbinputStream);
                // 读取属性值并赋给静态变量
                basicDataSource.setDriverClassName(properties.getProperty("driverClassName"));
                basicDataSource.setUrl(properties.getProperty("url"));
                basicDataSource.setUsername(properties.getProperty("username"));
                basicDataSource.setPassword(properties.getProperty("password"));
                basicData = basicDataSource;
                dbinputStream.close();
            } else {
                System.err.println("未找到资源: BasicDataSource.properties");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
