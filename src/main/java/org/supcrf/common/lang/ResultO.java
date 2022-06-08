package org.supcrf.common.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * @title: Result
 * @projectName learn-online_java
 * @description: TODO
 * @Author supcrf
 * @Version 1.0
 * @Date 2/6/2021 22:22
 */
@Data
public class ResultO implements Serializable {
    private int code;
    private String message;
    private Object data;

    public static ResultO ok(Object data) {
        return ok(200,"操作成功",data);
    }

    public static ResultO ok(int code, String message, Object data) {
        ResultO resultO = new ResultO();
        resultO.setCode(code);
        resultO.setMessage(message);
        resultO.setData(data);
        return resultO;
    }

    public static ResultO error(String message) {
        return error(400,message,null);
    }

    public static ResultO error(String message, Object data) {
        return error(400,message,data);
    }

    public static ResultO error(int code, String message, Object data) {
        ResultO resultO = new ResultO();
        resultO.setCode(code);
        resultO.setMessage(message);
        resultO.setData(data);
        return resultO;
    }
}