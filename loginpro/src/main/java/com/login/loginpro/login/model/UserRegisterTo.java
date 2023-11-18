package com.login.loginpro.login.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @project: loginpro
 * @title: UserRegisterTo （默认）
 * @description：用户注册参数
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 11:50
 */
@Data
public class UserRegisterTo implements Serializable {
    private String lid;
    private String password;
    private String email;
    private Integer phone;
    private String verCode;
}
