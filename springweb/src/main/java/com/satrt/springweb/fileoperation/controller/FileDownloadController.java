package com.satrt.springweb.fileoperation.controller;


import com.satrt.springweb.core.constant.Constant;
import com.satrt.springweb.core.model.entity.FileEntity;
import com.satrt.springweb.exception.sql.SqlServiceException;
import com.satrt.springweb.fileoperation.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 文件下载
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
    public void downloadFile(Integer fileId, HttpServletResponse response) throws IOException, SqlServiceException, URISyntaxException {
        //查询文件
        FileEntity fileEntity = fileService.getByDownId(fileId);
        // 设置响应头
        response.setContentType(fileEntity.getFileType());
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileEntity.getFileName(),"UTF-8"));
        // 替换为文件URL
        URI uri = new URI(Constant.ACCESS_PATH);
        URI uriFlier =new URI(fileEntity.getDownloadLink());
        URI concatenatedUri = uri.resolve(uri).resolve(uriFlier);
        // 获取文件输入流
        BufferedInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            URL url = concatenatedUri.toURL();
            inputStream = new BufferedInputStream(url.openStream());
            outputStream = response.getOutputStream();
            inputStream.transferTo(outputStream);
            outputStream.flush();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

}