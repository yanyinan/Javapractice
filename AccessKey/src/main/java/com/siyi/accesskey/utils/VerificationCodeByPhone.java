package com.siyi.accesskey.utils;

import java.security.SecureRandom;

public class VerificationCodeByPhone {

    // 生成6位随机手机验证码
    public static String generateVerificationCode() {
        // 验证码位数
        int codeLength = 6;

        // 验证码字符集
        String codeChars = "0123456789";

        // 用于生成随机验证码的 SecureRandom 对象
        SecureRandom secureRandom = new SecureRandom();

        // 用于存放生成的验证码
        StringBuilder verificationCode = new StringBuilder();

        // 随机生成验证码
        for (int i = 0; i < codeLength; i++) {
            char randomChar = codeChars.charAt(secureRandom.nextInt(codeChars.length()));
            verificationCode.append(randomChar);
        }

        return verificationCode.toString();
    }

    public static void main(String[] args) {
        String verificationCode = generateVerificationCode();
        System.out.println("生成的验证码: " + verificationCode);
    }
}
