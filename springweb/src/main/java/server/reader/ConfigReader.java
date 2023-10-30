package server.reader;

import server.utilentity.ServerDataSource;
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
     * 服务器配置
     */
    public static ServerDataSource serverDataSource;
    static {
        try {// 通过 ClassLoader 获取资源文件的输入流
            ClassLoader classLoader = ConfigReader.class.getClassLoader();
            ServerDataSource serverDataSource1 = new ServerDataSource();
            InputStream serverInputStream = classLoader.getResourceAsStream("server.properties");

            if (serverInputStream != null) {
                Properties properties = new Properties();
                properties.load(serverInputStream);
                // 读取属性值并赋给静态变量
                serverDataSource1.setPort(Integer.parseInt(properties.getProperty("port")));
                serverDataSource1.setWorkPath(properties.getProperty("workPath"));
                serverDataSource = serverDataSource1;
                serverInputStream.close();
            } else {
                System.err.println("Resource not found: client.properties");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
