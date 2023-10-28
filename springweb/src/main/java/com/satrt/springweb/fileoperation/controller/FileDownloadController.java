package com.satrt.springweb.fileoperation.controller;

import com.satrt.springweb.core.model.entity.FileEntity;
import com.satrt.springweb.exception.sql.SqlServiceException;
import com.satrt.springweb.fileoperation.service.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/28 14:05
 */
@RestController
public class FileDownloadController {
    private FileService fileService;

    FileDownloadController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/download")
    public void downloadFile(Integer fileId, HttpServletResponse response) throws IOException, SqlServiceException {
        System.out.println(fileId);
        //查询文件
        FileEntity fileEntity = fileService.getByDownId(fileId);
        // 设置响应头
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileEntity.getFileName().getBytes("gb2312"), "ISO8859-1"));


        String path = fileEntity.getDownloadLink();

        // 获取文件输入流 todo
        File file = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            // 获取输出流并写入文件内容
            OutputStream os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } finally {
            // 关闭输入流和输出流
            if (fis != null) {
                fis.close();
            }
        }
    }
}