package com.siyi.accesskey.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Set;

import static com.siyi.accesskey.constant.Constant.LOGIN_BLACK_LIST;
import static com.siyi.accesskey.constant.Constant.LOGIN_IP;


/**
 * @author siyi
 */
@Service
public class UserBlacklistService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    // 将用户添加到黑名单，同时记录错误次数
    public int addToBlacklistAndGetErrorCount(String ip) {
        // 记录错误次数，返回加 1 后的次数
        Long count = redisTemplate.opsForValue().increment(LOGIN_IP + ip, 1L);
        assert count != null;
        return count.intValue();
    }

    public void addToBlacklist(String ip) {
        // 将用户添加到黑名单集合
        redisTemplate.opsForSet().add(LOGIN_BLACK_LIST, ip);
    }

    // 获取用户错误次数
    public long getErrorCount(String ip) {
        String count = redisTemplate.opsForValue().get(LOGIN_IP + ip);
        return count != null ? Long.parseLong(count) : 0;
    }

    // 清除用户错误次数

    public void clearErrorCount(String ip) {
        redisTemplate.delete(LOGIN_IP + ip);
    }

    // 清空整个黑名单
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearBlacklist() {
        redisTemplate.delete(LOGIN_BLACK_LIST);
    }

    public boolean isUserInBlacklist(String ip) {
        Set<String> blacklistedUsers = redisTemplate.opsForSet().members(LOGIN_BLACK_LIST);
        return blacklistedUsers != null && blacklistedUsers.contains( ip);
    }

}
