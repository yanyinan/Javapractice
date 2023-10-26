package com.satrt.springweb.core.model.entity;

import com.satrt.springweb.core.constant.Constant;
import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体类
 *
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 15:02
 */
@Data
public class UserEntity implements Serializable {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 密码
     */
    private String password;
    /**
     * 账号状态（0正常 1停用）
     */
    private String status;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;

    public String getAvatar() {
        return Constant.UPLOAD_PATH  + userName+  avatar;
    }

    /**
     * 用户类型（0管理员，1普通用户）
     */
    private String userType;
    /**
     * 创建人的用户id
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;
}

