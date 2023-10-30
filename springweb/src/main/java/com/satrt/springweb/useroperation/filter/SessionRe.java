package com.satrt.springweb.useroperation.filter;

import com.satrt.springweb.core.constant.Constant;
import com.satrt.springweb.core.model.entity.UserEntity;
import com.satrt.springweb.exception.sql.SqlServiceException;
import com.satrt.springweb.useroperation.dao.UserDao;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 使用拦截器进行页面登录信息刷新
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/28 13:40
 */
//@WebFilter("/*")
public class SessionRe implements Filter {
    private UserDao dao = new UserDao();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        // 获取session域中指定元素数据，例如key为"username"的元素
        UserEntity userEntity = (UserEntity) session.getAttribute(Constant.LOGIN_USER);
        if (userEntity != null){
            try {
                UserEntity user = dao.selectById(userEntity.getId());
                session.setAttribute(Constant.LOGIN_USER, user);
            } catch (SqlServiceException e) {
                throw new RuntimeException(e);
            }
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}
