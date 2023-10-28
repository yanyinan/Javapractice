package com.satrt.springweb.core.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * ip 过滤
 * @author 26481
 */
@WebFilter("/*")
public class IpFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req  = (HttpServletRequest) request;

        String remoteHost = req.getRemoteHost();
        String uri = req.getRequestURI();

        System.out.println("ip: " + remoteHost + ", uri: " + uri + ", method: " + req.getMethod());

        chain.doFilter(request, response);
    }
}
