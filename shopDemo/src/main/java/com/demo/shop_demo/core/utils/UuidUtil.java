package com.demo.shop_demo.core.utils;

/**
 * uuid工具类
 *
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 2023/11/6
 */
public class UuidUtil {
    /**
     * 获取长度为32位的UUID字符串
     * @return 32位的UUID字符串
     */
    public static String getUUID32() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取一个32位的UUID字符串
     * @param prefix 前缀字符串
     * @return 生成的32位UUID字符串
     */
    public static String getUUID32(String prefix) {
        return prefix + java.util.UUID.randomUUID().toString().replace("-", "");
    }

}
