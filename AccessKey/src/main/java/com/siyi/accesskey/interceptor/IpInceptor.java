package com.siyi.accesskey.interceptor;

import com.siyi.accesskey.service.imp.IpBlacklistService;
import com.siyi.accesskey.service.imp.UserBlacklistService;
import com.siyi.accesskey.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @CreateTime: 2023-11-18  17:07
 * @Description: TODO 拦截黑名单中的ip/ 拦截账号错误到达五次的ip
 * @Version: 1.0
 */
@Component
@Slf4j
public class IpInceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
   private IpBlacklistService ipBlacklistService;
    @Autowired
    private UserBlacklistService userBlacklistService;
    @Autowired
    private IpUtil ipUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取 ip
        String ip = ipUtil.getIpAddr(request);
        //判断ip是否在黑名单中
        if (ipBlacklistService.isBlacklisted(ip)) {
            log.info("IP: {} - 疑似机器操作,已被拉黑", ip);
            // TODO 可以写一个页面，提示用户被拉黑
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("您已经被限制访问，请稍后再试");
            return false;
        }
        if (userBlacklistService.isUserInBlacklist(ip)) {
            log.info("IP: {} - IP错误到达五次,已被拉黑", ip);
            // TODO 可以写一个页面，提示用户被拉黑
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("您的操作存在违规，请稍后再试");
            return false;
        }
        return true;
    }
}
