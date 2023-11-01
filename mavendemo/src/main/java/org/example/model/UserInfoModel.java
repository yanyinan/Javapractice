package org.example.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: mavendemo
 * @ClassName UserInfoModel
 * @description:
 * @author: nanzhou
 * @create: 2023-11-01 22:57
 * @Version 1.0
 **/
@Data
public class UserInfoModel implements Serializable {
    private String id;

    private String username;

    private String password;

    private String name;

    private Integer gender;

    private Date birthday;

    private String phone;

    private String email;

    private String qq;

    private String img;

    private Date createDate;

    private Date updateDate;

    private Integer state;

    private Boolean del;
}