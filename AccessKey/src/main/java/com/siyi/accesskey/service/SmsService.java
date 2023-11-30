package com.siyi.accesskey.service;

public interface SmsService {

    /**
     * 发送短信
     *
     * @param phoneNumber   手机号码
     * @param templateParam 短信模板参数
     */
    void sendSms(String phoneNumber, String templateParam) throws Exception;
}