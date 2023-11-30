package com.siyi.accesskey.interceptor;

import com.alibaba.fastjson.JSON;
import com.siyi.accesskey.model.bean.AccessInfo;
import com.siyi.accesskey.service.imp.IpBlacklistService;
import com.siyi.accesskey.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.siyi.accesskey.constant.Constant.USER_IP;

/**
 * @CreateTime: 2023-11-18  16:26
 * @Description: TODO 拦截器实现IP防刷,记录违规ip
 * @Version: 1.0
 */
@Component
@Slf4j
public class CheckIpInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IpBlacklistService ipBlacklistService;
    @Autowired
    private IpUtil ipUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // 获取 ip
            String ip = ipUtil.getIpAddr(request);

            // 获取 ip 对应的访问信息的 JSON 字符串
            String accessInfoJson = redisTemplate.opsForValue().get(ip);

            // 记录第一次时间
            long current = System.currentTimeMillis();
            log.info("AccessInfo for IP: {} - Current Time: {}", ip, current);

            AccessInfo accessInfo;

            if (accessInfoJson == null) {
                // 第一次访问
                accessInfo = new AccessInfo();
                accessInfo.setCount(1);
                accessInfo.setMillis(current);
            } else {
                // 非第一次访问
                accessInfo = JSON.parseObject(accessInfoJson, AccessInfo.class);

                if ((current - accessInfo.getMillis()) > 1000) {
                    // 超过一秒连续访问
                    accessInfo.setCount(1);
                    accessInfo.setMillis(current);
                } else {
                    // 一秒内，判断次数
                    if (accessInfo.getCount() >10) {
                        // 存入ip黑名单
                        request.getRequestURI();
                        ipBlacklistService.addToBlacklist(ip);
                        // TODO 可以写一个页面，提示用户被拉黑,返回Resp
                        response.sendRedirect("/error");
                        return false;
                    } else {
                        accessInfo.setCount(accessInfo.getCount() + 1);
                    }
                }
            }

            // 将 AccessInfo 对象转换为 JSON 字符串并存入 Redis
            redisTemplate.opsForValue().set(USER_IP+ip, JSON.toJSONString(accessInfo));
        } catch (Exception e) {
            // 异常处理，根据实际情况进行处理
            log.error("IP拦截失败", e);
            response.sendRedirect("/error");
            return false;
        }

        return true;
    }
}

