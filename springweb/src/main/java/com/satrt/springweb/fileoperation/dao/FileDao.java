package com.satrt.springweb.fileoperation.dao;

import com.satrt.springweb.core.model.entity.FileEntity;
import com.satrt.springweb.core.utils.db.DbUtilsHelper;
import com.satrt.springweb.exception.sql.SqlServiceException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件数据层
 *
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/26 21:21
 */
@Repository
public class FileDao {
    /**
     * 查询所有文件
     * @return 返回文件列表
     */
    public List<FileEntity> selectAll() throws SqlServiceException {
        String sql = "SELECT id as id, file_name as fileName, file_type as fileType, size, createTime, createBy ,downloadLink from sys_file";
        return DbUtilsHelper.queryList(sql, FileEntity.class);
    }

    /**
     * 添加文件
     * @param fileEntity 文件对象
     * @return 返回 0 ，添加失败
     */
    public int fileAdd(FileEntity fileEntity) throws SqlServiceException {
        String sql = "insert into sys_file( file_name,file_type,size,downloadLink,createBy) values(?,?,?,?,?) ";
        return DbUtilsHelper.update(sql,fileEntity.getFileName(),fileEntity.getFileType(),fileEntity.getSize(),fileEntity.getDownloadLink(),fileEntity.getCreateBy());
    }

    /**
     * 根据文件 id 查询文件
     * @param id 文件id
     * @return 返回文件
     */
    public FileEntity selectById(Integer id) throws SqlServiceException {
        String sql = "SELECT id as id, file_name as fileName, file_type as fileType, size, createTime, createBy ,downloadLink from sys_file where id = ?";
        return DbUtilsHelper.queryOne(sql, FileEntity.class, id);
    }


    public void updateAvatar(String avatarPath, int id) throws SqlServiceException {
        String sql = "update sys_user set avatar = ?   where id = ?";
        DbUtilsHelper.update(sql,avatarPath,id);
    }

    public FileEntity selectDownById(Integer fileId) throws SqlServiceException {
        String sql ="select file_name as fileName,downloadLink from sys_file where id = ?";
        return DbUtilsHelper.queryOne(sql,FileEntity.class,fileId);
    }
}
