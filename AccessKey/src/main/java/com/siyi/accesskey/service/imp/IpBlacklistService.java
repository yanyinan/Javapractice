package com.siyi.accesskey.service.imp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.siyi.accesskey.constant.Constant.USER_BLACK_LIST_IP;

@Service
public class IpBlacklistService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 将IP添加到黑名单，设置过期时间为一天
    public void addToBlacklist(String ip) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        long expirationTime = System.currentTimeMillis() + 24 * 60 * 60 * 1000; // 24 hours in milliseconds
        zSetOperations.add(USER_BLACK_LIST_IP, ip, expirationTime);
    }

    // 判断IP是否在黑名单中
    public boolean isBlacklisted(String ip) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.score(USER_BLACK_LIST_IP, ip) != null;
    }

    // 获取过期的IP
    public Set<String> getExpiredIps() {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        long currentTime = System.currentTimeMillis();
        return zSetOperations.rangeByScore(USER_BLACK_LIST_IP, 0, currentTime);
    }

    // 移除黑名单中的IP
    public void removeFromBlacklist(String ip) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.remove(USER_BLACK_LIST_IP, ip);
    }

    // 每天的固定时间清理过期的IP
    @Scheduled(cron = "0 0 12 * * ?") // 每天中午12点触发
    public void scheduledClearExpiredIps() {
        clearExpiredIps();
    }

    // 清理过期的IP
    private void clearExpiredIps() {
        Set<String> expiredIps = getExpiredIps();
        for (String ip : expiredIps) {
            removeFromBlacklist(ip);
        }
    }
}
