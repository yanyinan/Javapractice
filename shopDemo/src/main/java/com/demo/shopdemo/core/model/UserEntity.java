package com.demo.shopdemo.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录
 *
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 2023/11/4
 */
@Data
public class UserEntity implements Serializable {

    /**
     * 用户编号，自定义
     */

    private String id;
    /**
     * 用户名
     */

    private String username;
    /**
     * 密码(需要加密)
     */

    private String password;
    /**
     * 真实姓名
     */

    private String name;
    /**
     * 手机号
     */

    private String phone;
    /**
     * 邮箱
     */

    private String email;
    /**
     * 头像
     */

    private String avatar;
    /**
     * 状态 0表示禁用 1表示正常
     */

    private Integer state;
    /**
     * 创建时间
     */

    private Date createDate;
    /**
     * 更新时间
     */

    private Date updateDate;
    /**
     * 用户类型（0，管理员；1，普通用户）
     */

    private Integer usertype;

    private Boolean login;
}
