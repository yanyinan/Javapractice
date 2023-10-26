package com.satrt.springweb.core.utils.upload;

import com.satrt.springweb.core.constant.Constant;
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
    public static String saveFile(MultipartFile multipartFile, String submittedFileName,String Filetype) throws IOException {
        // 按照日期存储. 获取日期的字符串 替换 - // 20231017
        String date = LocalDate.now().toString().replace("-", "");

        // 保存文件的路径
        // 根据类型改变文件路径 todo
        File file = new File(Constant.UPLOAD_PATH, date);

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

        return date + "/" + fileName + substring;
    }
}
