package com.login.loginpro.core.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @project: loginpro
 * @title: ReadTOString （默认）
 * @description： 读取文件内容 读取为保存为字符串
 * @author: Nanzhou
 * @version: v0.0.1
 * @Date: 20:28
 */
public class ReadFileToString {
    public static String readFileToString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}


