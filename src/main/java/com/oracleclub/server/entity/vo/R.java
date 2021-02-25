package com.oracleclub.server.entity.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 通用响应
 * @author RETURN
 * @date 2020/8/14 0:24
 */
@Data
@Builder
public class R {
    private Object data;
    private String msg;
    private String result;

    public static R success(String msg, Object data){
           return R.builder().result("ok").data(data).msg(msg).build();
    }
    public static R success(String msg){
        return R.builder().result("ok").msg(msg).build();
    }

    public static R failed(String msg){
        return R.builder().result("failed").msg(msg).build();
    }
    public static R failed(String msg, Object data){
        return R.builder().result("failed").data(data).msg(msg).build();
    }
}
