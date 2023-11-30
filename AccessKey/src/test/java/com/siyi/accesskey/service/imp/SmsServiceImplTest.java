package com.siyi.accesskey.service.imp;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.siyi.accesskey.service.SmsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: SiYi
 * @CreateTime: 2023-11-13  13:09
 * @Description: TODO
 * @Version: 1.0
 */
@SpringBootTest
class SmsServiceImplTest {
@Autowired
    SmsService smsService;
    @Test
    void sendSms() {
        try {
            smsService.sendSms("19993013190","{\"code\":\"571789\"}");
            System.out.println("发送成功");
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
}