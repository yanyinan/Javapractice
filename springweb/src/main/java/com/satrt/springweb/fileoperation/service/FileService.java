package com.satrt.springweb.fileoperation.service;

import com.satrt.springweb.core.model.entity.FileEntity;
import com.satrt.springweb.core.model.entity.UserEntity;
import com.satrt.springweb.core.utils.upload.Upload;
import com.satrt.springweb.exception.service.ServiceException;
import com.satrt.springweb.exception.sql.SqlServiceException;
import com.satrt.springweb.fileoperation.dao.FileDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/26 21:13
 */
@Service
public class FileService {
    private  FileDao fileDao;
    FileService(FileDao fileDao){
        this.fileDao = fileDao;
    }

    public  List<FileEntity> fileDirectory() throws SqlServiceException {
        return fileDao.selectAll();
    }
}
