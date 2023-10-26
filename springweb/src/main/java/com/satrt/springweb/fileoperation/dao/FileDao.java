package com.satrt.springweb.fileoperation.dao;

import com.satrt.springweb.core.model.entity.FileEntity;
import com.satrt.springweb.core.utils.db.DbUtilsHelper;
import com.satrt.springweb.exception.sql.SqlServiceException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件数据层
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/26 21:21
 */
@Repository
public class FileDao {

    public List<FileEntity> selectAll() throws SqlServiceException {
        String sql = "SELECT id, name, fileType, size, createTime, downloadLink from sys_file";
        return DbUtilsHelper.queryList(sql,FileEntity.class);
    }

    public int fileAdd(FileEntity fileEntity) throws SqlServiceException {
        String sql="";
        return DbUtilsHelper.update(sql);
    }

    public FileEntity selectById(Integer id) throws SqlServiceException {
        String sql = "";
        return DbUtilsHelper.queryOne(sql,FileEntity.class,id);
    }
}
