package com.siyi.accesskey.config;

import com.siyi.accesskey.interceptor.CheckIpInterceptor;
import com.siyi.accesskey.interceptor.IpInceptor;
import com.siyi.accesskey.interceptor.LoginRememberInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @CreateTime: 2023-11-18  12:10
 * @Description: TODO
 * @Version: 1.0
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Autowired
    private IpInceptor ipInceptor;
    @Autowired
    private CheckIpInterceptor checkIpInterceptor;
    @Autowired
    private LoginRememberInterceptor loginRememberInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        registry.addInterceptor(ipInceptor).addPathPatterns("/**").excludePathPatterns("/service/**", "/js/**", "/images/**", "/layui/**");
        registry.addInterceptor(checkIpInterceptor).addPathPatterns("/**").excludePathPatterns("/service/**", "/js/**", "/images/**", "/layui/**");
        registry.addInterceptor(loginRememberInterceptor).addPathPatterns("/login/**","/login").excludePathPatterns("/service/**", "/js/**", "/images/**", "/layui/**");
    }
}
