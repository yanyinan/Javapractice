package com.demo.shop_demo.user_operation.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 26481
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnMessage implements Serializable {

    private Integer code;

    private String msg;

    private Object data;
    public static ReturnMessage OperationSuccess() {
        return new ReturnMessage(200, "操作成功", null);
    }
    public static ReturnMessage Error(String msg) {
        return new ReturnMessage(500, msg, null);
    }
    public static ReturnMessage ReturnMessage(Object data) {
        return new ReturnMessage(200, "操作成功", data);
    }

}