package com.satrt.springweb.fileoperation.service;

import com.satrt.springweb.core.constant.FileServiceConstant;
import com.satrt.springweb.core.model.entity.FileEntity;
import com.satrt.springweb.exception.service.ServiceException;
import com.satrt.springweb.exception.sql.SqlServiceException;
import com.satrt.springweb.fileoperation.dao.FileDao;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.satrt.springweb.core.utils.upload.Upload.uploadFile;

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

    /**
     * 文件列表
     * @return 返回文件列表
     */
    public  List<FileEntity> fileDirectory(FileEntity fileEntity,String username, int pageNum, int pageSize) throws SqlServiceException {
        return fileDao.selectLoginUserfile(fileEntity,username,pageNum,pageSize);
    }

    /**
     * 添加文件
     * @param file 文件
     * @param id 用户id
     * @return 返回 0 添加失败
     */
    public int save(MultipartFile file, String username) throws IOException, SqlServiceException {
        String path = uploadFile(file, file.getOriginalFilename(),username, FileServiceConstant.UPLOAD_FILE_FLAG);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setSize(file.getSize());
        fileEntity.setCreateBy(username);
        //设置地址
        fileEntity.setDownloadLink("/web/" + path);
        return fileDao.fileAdd(fileEntity);
    }

    /**
     * 根据文件 id 查找文件
     * @param id 文件id
     * @return 返回文件
     */
    public FileEntity getById(Integer id) throws SqlServiceException {
        return fileDao.selectById(id);
    }

    /**
     * 根据文件 id 查找指定文件
     * @param fileId 文件id
     * @return 文件实体
     */
    public FileEntity getByDownId(Integer fileId) throws SqlServiceException {
        return fileDao.selectDownById(fileId);
    }

    /**
     * 获取登录用户文件个数
     * @param fileEntity 文件实体
     * @param userName 用户名
     * @return 返回个数
     */
    public int getTotal(FileEntity fileEntity, String userName) throws SqlServiceException {
        if (ObjectUtils.isEmpty(fileEntity)){
            System.out.println("参数不能为空");
        }
        return fileDao.fileTotal(fileEntity,userName);
    }
}
