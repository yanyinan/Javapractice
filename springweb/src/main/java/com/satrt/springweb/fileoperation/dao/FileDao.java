package com.satrt.springweb.fileoperation.dao;

import com.satrt.springweb.core.model.entity.FileEntity;
import com.satrt.springweb.core.utils.db.DbUtilsHelper;
import com.satrt.springweb.exception.sql.SqlServiceException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
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
}
