package com.satrt.springweb.fileoperation.controller;

import com.satrt.springweb.core.constant.Constant;
import com.satrt.springweb.core.model.entity.FileEntity;
import com.satrt.springweb.exception.sql.SqlServiceException;
import com.satrt.springweb.fileoperation.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URL;

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
    @Autowired
    private  ResourceLoader resourceLoader;
    private static final long serialVersionUID = 1L;
    @GetMapping("/download")
    public void downloadFile(Integer fileId, HttpServletResponse response) throws IOException, SqlServiceException {
        //查询文件
        FileEntity fileEntity = fileService.getByDownId(fileId);
        // 设置响应头
        response.setContentType(fileEntity.getFileType());
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileEntity.getFileName().getBytes("gb2312"), "ISO8859-1"));

        String path = "http://localhost:8080/"+fileEntity.getDownloadLink();
        // 替换为实际的图片URL
        String imageUrl = "http://localhost:8080/web/kaifamiao/upload/20231027/38259824be8645089f951a6694057496.png";
        // 获取文件输入流 todo
        InputStream inputStream = null;
        try {
            URL url = new URL(path);
            inputStream = url.openStream();
            // 获取输出流并写入文件内容
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}