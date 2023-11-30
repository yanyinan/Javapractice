package com.siyi.accesskey.model.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: SiYi
 * @CreateTime: 2023-11-15  00:02
 * @Description: TODO
 * @Version: 1.0
 */
@ToString(callSuper = true)
@Data
public class LoginUserCodeVO extends LoginUser implements Serializable {
    private String code;
    private String captcha;
    private boolean rememberMe;
}
