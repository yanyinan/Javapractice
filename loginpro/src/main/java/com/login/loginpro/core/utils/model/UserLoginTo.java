package com.login.loginpro.core.utils.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @project: loginpro
 * @title: UserLoginTo （默认）
 * @description：登录参数
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 18:24
 */
@Data
public class UserLoginTo implements Serializable {
    private String lid;
    private String password;
    private String captcha;
    private String email;
    private Integer phone;
    private String code;
}
