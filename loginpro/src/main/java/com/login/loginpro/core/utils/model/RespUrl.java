package com.login.loginpro.core.utils.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @project: loginpro
 * @title: RespUrl （默认）
 * @description: 返回请求
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 10:31
 */
@Data
public class RespUrl implements Serializable {

    private String url;
    private String message;
    public RespUrl(String url, String message) {
        this.url = url;
        this.message = message;
    }
}
