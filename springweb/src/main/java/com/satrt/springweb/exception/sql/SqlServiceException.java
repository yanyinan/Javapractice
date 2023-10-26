package com.satrt.springweb.exception.sql;

/**
 * 数据库操作异常
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/26 17:29
 */
public class SqlServiceException extends Exception{
    /**
     * 响应码
     * 未知错误，0
     * 查询错误，1
     * 删除错误，2
     * 更新错误，3
     * 连接错误，4
     * 注册异常，5
     */
    private int code;

    public SqlServiceException() {
        this("未知错误",0);
    }

    public SqlServiceException(String message, int code) {
        super(message);
        this.code = code;
    }

    public SqlServiceException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public SqlServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
