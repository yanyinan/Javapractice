package server;

import server.finalpool.FileConstant;
import server.reader.ConfigReader;
import server.utils.FileUtil;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//todo 服务端配置

/**
 * @author Administrator
 */
public class ServerService {
    private static final int PORT = ConfigReader.serverDataSource.getPort();
    private static final String FILE_PATH = ConfigReader.serverDataSource.getWorkPath();
    public static void start() {

//        DataInputStream dataInputStream = null;
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("ServerService listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                System.out.println(clientSocket);
                System.out.println(clientSocket.getInputStream());
                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());

                // 接收字符串
                String receivedString = dataInputStream.readUTF();
                System.out.println("Received String: " + receivedString);

                // 提取文件路径信息
                String[] parts = receivedString.split("\\*");
                String path = FILE_PATH + File.separator + parts[2];
                if (!parts[1].equals(FileConstant.TYPE_DELETE)){


                    // 接收文件并保存到指定目录
                    try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = dataInputStream.read(buffer)) != -1) {
                            fileOutputStream.write(buffer, 0, bytesRead);
                        }
                        System.out.println("更新或增加: " + path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    FileUtil.deleteFile(path);
                    System.out.println("删除成功");
                }

                dataInputStream.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}