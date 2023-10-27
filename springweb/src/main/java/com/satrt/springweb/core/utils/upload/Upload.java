package com.satrt.springweb.core.utils.upload;

import com.satrt.springweb.core.constant.Constant;
import com.satrt.springweb.core.constant.FileServiceConstant;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;


/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/26 21:10
 */
public class Upload {
    public static String saveUserFile(MultipartFile multipartFile, String submittedFileName,String username,String uploadType) throws IOException {
        // 按照日期存储. 获取日期的字符串 替换 - // 20231017
        String date = LocalDate.now().toString().replace("-", "");

        // 保存文件的路径
        // 根据类型改变文件路径 todo
        File file = new File(Constant.UPLOAD_PATH+"/"+username, date);
        // 判断文件夹是否存在
        if (!file.exists()){
            // 不存在创建
            file.mkdirs();
        }
        //todo 文件冲突
            /*
             重命名文件，防止文件冲突
                时间戳
                UUID
             */
        // .jpg
        String substring = submittedFileName.substring(submittedFileName.lastIndexOf("."));


        String fileName = UUID.randomUUID().toString().replace("-", "");

        //存储
        // 存文件  d:/temp/upload/20231017/fileName + substring
        multipartFile.transferTo(new File(file.getAbsolutePath(),fileName + substring));

        return username+ "/" +date + "/" + fileName + substring;
    }
    public static String uploadFile(MultipartFile multipartFile, String submittedFileName,String username,int uploadType) throws IOException {

        // 根据类型改变文件路径
        StringBuffer filepath = typeFile(username,uploadType);

        // 保存文件的路径
        File file = new File(Constant.UPLOAD_PATH, String.valueOf(filepath));

        // 判断文件夹是否存在
        if (!file.exists()){
            // 不存在创建
            file.mkdirs();
        }
        //todo 文件冲突
            /*
             重命名文件，防止文件冲突
                时间戳
                UUID
             */
        // .jpg
        //获取文件后缀
        String substring = submittedFileName.substring(submittedFileName.lastIndexOf("."));

        String fileName = UUID.randomUUID().toString().replace("-", "") +substring;
        //存储
        // 存文件  d:/temp/upload/20231017/fileName + substring
        multipartFile.transferTo(new File(file.getAbsolutePath(),fileName));

        return filepath + "/" + fileName ;
    }

    /**
     *  根据类型改变文件路径
     * @param username 用户名
     * @param uploadType 文件类型
     * @return 文件路径
     */
    private static StringBuffer typeFile(String username, int uploadType) {
        StringBuffer filepath =new StringBuffer();
        if (FileServiceConstant.AVATAR_FILE_FLAG.equals(uploadType)){
            filepath.append(username+"/");
            filepath.append(FileServiceConstant.AVATAR_FILE+"/");
        }else if(FileServiceConstant.UPLOAD_FILE_FLAG.equals(uploadType)){
            filepath.append(username+"/");
            filepath.append(FileServiceConstant.UPLOAD_FILE+"/");
            //按照日期存储. 获取日期的字符串 替换 - // 20231017
            filepath.append(LocalDate.now().toString().replace("-", ""));
        }
        return filepath;
    }

}
