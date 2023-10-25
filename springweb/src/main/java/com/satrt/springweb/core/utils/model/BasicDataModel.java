package com.satrt.springweb.core.utils.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/25 14:37
 */
@Data
public class BasicDataModel implements Serializable {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}