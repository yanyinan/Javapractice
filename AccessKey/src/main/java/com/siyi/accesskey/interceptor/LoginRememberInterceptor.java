package com.siyi.accesskey.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.siyi.accesskey.model.domain.LoginUser;
import com.siyi.accesskey.service.UserService;
import com.siyi.accesskey.service.imp.UserBlacklistService;
import com.siyi.accesskey.utils.AesUtil;
import com.siyi.accesskey.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.siyi.accesskey.constant.Constant.*;

/**
 * @author 26481
 * @CreateTime: 2023-11-21  22:55
 * @Description: TODO 记住密码
 * @Version: 1.0
 */
@Slf4j
@Component
public class LoginRememberInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Autowired
    private UserBlacklistService userBlacklistService;
    @Autowired
    private IpUtil ipUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断ip错误次数
        long errorCount = userBlacklistService.getErrorCount(ipUtil.getIpAddr(request));
        request.getSession().setAttribute(SHOW_CAPTCHA, false);
        // 如果错误次数超过设定的阈值，则将一个标志位存入会话中
        if (errorCount >=1) {
            request.getSession().setAttribute(SHOW_CAPTCHA, true);
        }
        //获取请求方法
        String method = request.getMethod();
        if ("POST".equalsIgnoreCase(method)) {
            return true;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (REMEMBER_ME.equals(cookie.getName())) {
                    String value = cookie.getValue();
                    //数据库查询
                    QueryWrapper<LoginUser> queryWrapper = new QueryWrapper<>();
                    queryWrapper.select("user_id", "username", "phone", "password", "email", "user_status");
                    queryWrapper.and(i -> {
                        try {
                            i.eq("username", AesUtil.AES_CBC_Decrypt_String(value))
                                    .or().eq("phone", AesUtil.AES_CBC_Decrypt_String(value))
                                    .or().eq("email", AesUtil.AES_CBC_Decrypt_String(value));
                        } catch (Exception e) {
                            log.error("解密失败");
                        }
                    });

                    LoginUser loginUser = userService.getOne(queryWrapper);
                    if (loginUser != null) {
                        request.getSession().setAttribute(USER_LOGIN_STATE, loginUser);
                        response.sendRedirect("/index");
                        return false;
                    }
                }
            }
        }





        return true;
    }
}
