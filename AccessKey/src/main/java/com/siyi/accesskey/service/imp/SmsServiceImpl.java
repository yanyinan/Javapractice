package com.siyi.accesskey.service.imp;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.siyi.accesskey.service.SmsService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.siyi.accesskey.constant.Constant.SMS_REPLACE;
import static com.siyi.accesskey.constant.Constant.SMS_TEMPLATE;

/**
 * @Author: SiYi
 * @CreateTime: 2023-11-13  12:56
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
    @Value("${aliyun.sms.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.sms.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.sign-name}")
    private String signName;

    @Value("${aliyun.sms.template-code}")
    private String templateCode;
    @Override
    public void sendSms(String phoneNumber, String contentCode) throws Exception {
        //替换模板中的验证码
        String templateParam = SMS_TEMPLATE.replace(SMS_REPLACE, contentCode);

        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";

       Client client = new  com.aliyun.dysmsapi20170525.Client(config);

        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setPhoneNumbers(phoneNumber)
                .setTemplateParam(templateParam);

        RuntimeOptions runtime = new RuntimeOptions();

        try {
            client.sendSmsWithOptions(sendSmsRequest, runtime);

        } catch (TeaException error) {
            // 处理异常
            log.error("发送短信失败", error);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 处理异常
            log.error("发送短信失败", error);
        }
    }

}
