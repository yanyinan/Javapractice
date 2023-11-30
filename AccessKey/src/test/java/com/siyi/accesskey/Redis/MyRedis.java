package com.siyi.accesskey.Redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Author: SiYi
 * @CreateTime: 2023-11-15  00:59
 * @Description: TODO
 * @Version: 1.0
 */
@SpringBootTest
 class MyRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void test() {
        String key ="sms";
        String value = "600389";
        redisTemplate.opsForValue().set(key,value,300, TimeUnit.SECONDS);
        System.out.println("存入成功");
    }
    @Test
    void test2() {
        String key ="sms";
        Long expire = redisTemplate.getExpire(key);
        if(expire == -2){
            System.out.println("验证码过期");
        }
        String value = redisTemplate.opsForValue().get(key);
        System.out.println("value="+value);
    }
}
