package com.siyi.accesskey.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @CreateTime: 2023-11-14  11:03
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resp implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;
    private Object data;

    public static Resp ok( ) {
        return new Resp(200, "操作成功", null);
    }
    public static Resp ok(Object data) {
        return new Resp(200, "操作成功", data);
    }
    public static Resp ok(String msg, Object data) {
        return new Resp(200, msg, data);
    }
    public static Resp error(String msg) {
        return new Resp(500, msg, null);
    }
    public static Resp error(String msg, Object data) {
        return new Resp(500, msg, data);
    }
    public static Resp error(int code, String msg, Object data) {
        return new Resp(code, msg, data);
    }

    /**
     * 将参数错误存入集合
     *
     * @param map           错误集合
     * @param bindingResult 错误信息
     */
    private static void error(Map<String, String> map, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    /**
     * 将错误信息存入集合
     *
     * @param bindingResult 错误信息
     * @return 错误集合
     */
    public static Resp getErrorMap(BindingResult bindingResult) {
        Map<String, String> map = new HashMap<>();
        error(map, bindingResult);
        return Resp.error("参数校验失败!", map);
    }

}
