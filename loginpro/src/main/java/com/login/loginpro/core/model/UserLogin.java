package com.login.loginpro.core.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户登录信息表
 * @TableName user_login
 */
@Data
public class UserLogin implements Serializable {
    /**
     * 用户账号
     */
    private String lid;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private Integer phone;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}