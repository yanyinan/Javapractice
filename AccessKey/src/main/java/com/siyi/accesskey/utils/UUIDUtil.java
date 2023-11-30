package com.siyi.accesskey.utils;

import java.util.UUID;

public class UUIDUtil {

    /**
     * 生成一个UUID字符串
     *
     * @return 生成的UUID字符串
     */
    public static String uuid(){
        return UUID.randomUUID().toString();
    }


    /**
     * 生成一个32位的UUID（通用唯一识别码）
     * @return 一个32位的UUID，没有连字符
     */
    public static String uuid32(){
        return uuid().replace("-", "");
    }


}
