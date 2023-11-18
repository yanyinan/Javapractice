package com.login.loginpro.core.utils.model;

import java.util.HashMap;

public class Resp extends HashMap<String, Object> {


    public static Resp ok(){
        Resp resp = new Resp();
        resp.put("code", 200);
        return resp;
    }
    public static Resp ok(String msg){
        Resp resp = new Resp();
        resp.put("code", 200);
        resp.put("message", msg);
        return resp;
    }

    public static Resp ok(Object data){
        Resp resp = new Resp();
        resp.put("code", 200);
        resp.put("data", data);
        return resp;
    }

    public static Resp fail(String msg){
        Resp resp = new Resp();
        resp.put("code", 500);
        resp.put("message", msg);
        return resp;
    }

    @Override
    public Resp put(String key, Object value){
        super.put(key, value);
        return this;
    }
}
