package com.client.sdk.base;

import com.client.utils.ResultUtil;
import com.client.utils.StatusUtil;

/**
 * Created by Ethan.Yuan on 2018/4/26.
 */
public class BaseClass {

    public static ResultUtil success(Object data){
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(StatusUtil.SUCCESS);
        resultUtil.setData(data);
        return resultUtil;
    }

    public static ResultUtil failed(String code){
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(code);
        return resultUtil;
    }
    public static ResultUtil failed(String code,String msg){
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(code);
        resultUtil.setMsg(msg);
        return resultUtil;
    }

    public static ResultUtil failed(String code, Object data){
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(code);
        resultUtil.setData(data);
        return resultUtil;
    }
}
