package com.satrt.springweb.core.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件实体类
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/26 17:24
 */
@Data
public class FileEntity implements Serializable {
    private int id;
    private String name;
    private String fileType;
    private long size;
    private Date createTime;
    private String createBy;
    private String downloadLink;
}
