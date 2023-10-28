package com.satrt.springweb.useroperation.filter;

import javax.servlet.annotation.WebFilter;
import java.util.List;

/**
 * 用户操作拦截器
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/28 15:16
 */
@WebFilter("/userOperate")
public class OperatorFilter {
    private final List<String> operation = List.of("/edit", "/login", "/register", "/captcha");
}
