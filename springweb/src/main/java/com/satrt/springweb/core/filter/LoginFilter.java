package com.satrt.springweb.core.filter;

import com.satrt.springweb.core.constant.Constant;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 登录过滤
 * @author 26481
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    private List<String> excludePaths = List.of("/", "/login", "/register", "/captcha");
    private String[] resourcePaths = {"/css/", "/js/", "/fonts/", "/images/","/assists/","/backgrouder/","/login/"};
    private List<String> paths = List.of("/web/");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 转换为 HttpServletRequest 和 HttpServletResponse
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 获取请求路径
        String requestURI = req.getRequestURI();
        // 获取上下文路径
        String contextPath = req.getContextPath();
        // 截取请求路径
        String path = requestURI.substring(contextPath.length());

        // 是否是静态资源请求
        for (String resourcePath : resourcePaths) {
            if (path.startsWith(resourcePath)){
                // 是静态资源请求，放行
                chain.doFilter(request, response);
                return;
            }
        }

        // 是否是排除的路径
        if (excludePaths.contains(path)){
            // 是排除的路径，放行
            chain.doFilter(request, response);
            return;
        }
        // 判断用户是否登录
        Object loginUser = req.getSession().getAttribute(Constant.LOGIN_USER);
        if (loginUser == null){
            // 没有登录，重定向到登录页面
            resp.sendRedirect(contextPath + "/login");
            return;
        }
        if (!paths.contains(path)){
            chain.doFilter(request, response);
        }
//        chain.doFilter(request, response);
    }
}
