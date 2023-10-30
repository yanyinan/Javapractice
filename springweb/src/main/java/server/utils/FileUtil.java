package server.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/20 20:33
 */
public class FileUtil {

    /**
     * 创建文件
     * @param filePath 文件路径
     * @return 是否创建成功
     */
    public static boolean createFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     * @return 是否删除成功
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        return file.delete();
    }

    /**
     * 修改文件内容
     * @param filePath 文件路径
     * @param content 新的内容
     * @return 是否修改成功
     */
    public static boolean updateFileContent(String filePath, String content) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读取文件内容
     * @param filePath 文件路径
     * @return 文件内容
     */
    public static String readFileContent(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取指定目录下的所有文件
     * @param directoryPath 目录路径
     * @return 文件列表
     */
    public static List<String> getAllFiles(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            return new ArrayList<>();
        }
        File[] files = directory.listFiles();
        List<String> fileList = new ArrayList<>();
        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                fileList.add(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                fileList.addAll(getAllFiles(file.getAbsolutePath()));
            }
        }
        return fileList;
    }
}
