package com.satrt.springweb.useroperation.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用户操作拦截器
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/28 15:16
 */
//@WebFilter("/userOperate")
public class OperatorFilter implements Filter {
    //todo 拦截不到
    private final List<String> operation = List.of("/edit", "/userAdd", "/delete", "/avatarEdit");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 转换为 HttpServletRequest 和 HttpServletResponse
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // 获取请求路径
        String requestURI = req.getRequestURI();
        // 获取上下文路径
        String contextPath = req.getContextPath();
        // 截取请求路径
        String path = requestURI.substring(contextPath.length());

        // 是否是排除的路径
        if (operation.contains(path)){
            // 校验
            String password = (String) req.getSession().getAttribute("verification");;
            if (password != null ){
                // 校验成功
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }else {
                resp.sendRedirect(contextPath + "backgrounder/userOperation/passwordVerification");
            }
        }
        // 是排除的路径，放行
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
