package server.finalpool;

import server.reader.ConfigReader;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/23 9:55
 */
public class FileConstant {
    public static final String UPLOAD_PATH = ConfigReader.serverDataSource.getWorkPath();
    /**
     * 初始化加载资源路径
     */
    public final static String INFO_PATH = "client.properties";
    /**
     * 用于确定命令操作增加
     */
    public static final String TYPE_ADD = "CREATE_FILE";
    /**
     * 用于确定命令操作修改
     */
    public static final String TYPE_UPDATE = "UPDATE_FILE";
    /**
     * 用于确定命令操作删除
     */
    public static final String TYPE_DELETE = "DELETE_FILE";
}
