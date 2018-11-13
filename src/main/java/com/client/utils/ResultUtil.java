package com.client.utils;

import java.util.Map;

/**
 * Created by Ethan.Yuan on 2018/4/19.
 */
public class ResultUtil<T> {
    /**
     * 返回代码
     */
    private String code;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
