package com.siyi.accesskey.utils;

import com.siyi.accesskey.model.domain.LoginUser;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * MD5 工具类
 */
@Slf4j
public class MD5Util {

    /**
     * MD5 加密
     *
     * @param source 原文
     * @return 密文
     */
    public static String md5(String source) {
        try {
            // 创建MD5消息摘要对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 将输入字符串转换为字节数组
            byte[] inputBytes = source.getBytes();

            // 计算MD5摘要
            byte[] md5Bytes = md.digest(inputBytes);

            // 将字节数组转换为16进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : md5Bytes) {
                sb.append(String.format("%02x", b));
            }

            String md5Hash = sb.toString();

            System.out.println("MD5 Hash: " + md5Hash);
            return md5Hash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * MD5 加盐加密
     *
     * @param source 原文
     * @param salt   盐值
     * @return 密文
     */
    public static String md5(String source, String salt) {
        try {
            // 生成随机盐值
            SecureRandom random = new SecureRandom();
            byte[] saltByte = salt.getBytes();

            // 创建MD5消息摘要对象
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 将密码和盐值连接
            byte[] passwordWithSalt = concatenateByteArrays(source.getBytes(), saltByte);

            // 计算MD5摘要
            byte[] md5Bytes = md.digest(passwordWithSalt);

            // 将字节数组转换为16进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : md5Bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return source;
    }

    private static byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
    /**
     * 注册时的密码加密
     *
     * @param loginUser 用户
     * @return 加密后的密码
     */
    public static String getEncodePassword(LoginUser loginUser) {
        if (loginUser.getUsername()!=null ) {
            //用户名不为空,这是用户名注册
            String base = Base64.getEncoder().encodeToString(loginUser.getUsername().getBytes());
            String salt = getEncodePassword(loginUser.getUsername(), base);
            return getEncodePassword(loginUser.getPassword(), salt);
        }else if ( loginUser.getEmail()!=null) {
            //邮箱不为空,这是邮箱注册
            String base = Base64.getEncoder().encodeToString(loginUser.getEmail().getBytes());
            String salt = getEncodePassword(loginUser.getEmail(), base);
            return getEncodePassword(loginUser.getPassword(), salt);
        }else if ( loginUser.getPhone()!=null){
            //手机号不为空,这是手机号注册
            String base = Base64.getEncoder().encodeToString(loginUser.getPhone().getBytes());
            String salt = getEncodePassword(loginUser.getPhone(), base);
            return getEncodePassword(loginUser.getPassword(), salt);
        }
        log.error("密码加密异常");
        return null;
    }
    //登录时加密


    public static String getEncodePassword(String password, String salt) {
        return md5(md5(password, salt));
    }

    public static void main(String[] args) {
        String md5 = md5(md5("123456", "zhangsan"));
        System.out.println(md5);
    }
}
