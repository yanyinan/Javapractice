package xyz.nanzhoufy.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体类
 * 表名：user
 */
@Data
public class User implements Serializable {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

}
