package com.client.utils.PayUtil;


import com.alibaba.fastjson.JSONObject;
import com.client.exception.ApiException;
import com.client.utils.StatusUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PayUtil extends BasePayUtil{
    private static Logger logger = LoggerFactory.getLogger(PayUtil.class);

    /**
     * 发送请求
     */
    public static String doPost(String params) throws ApiException {
        Map<String,String> mapParams = JSONObject.parseObject(params,Map.class);
        return doPost(mapParams);
    }

    public static String doPost(Map<String,String> mapParams) throws ApiException{
        //判断请求参数是否为空
        if(mapParams.get("url")==null||mapParams.get("url").equals("")){
            throw new ApiException(StatusUtil.URL_INVALID,"request url is empty");
        }else if(mapParams.get("params")==null||mapParams.get("params").equals("")){
            throw new ApiException(StatusUtil.PARAMETER_INVALID,"request param is empty");
        }else if(mapParams.get("token")==null||mapParams.get("token").equals("")){
            throw new ApiException(StatusUtil.HEADER_ENCODE_FAILED,"request token is empty");
        }

        String result = null;
        try {
            result = PayUtil.doPost(mapParams.get("url"), mapParams.get("params"), mapParams.get("token"));
            if(result == null){
                //返回值为空，抛错
                throw new Exception();
            }
        } catch (Exception e) {
            throw new ApiException(StatusUtil.PRE_PAY_REQUEST_FAILED, "prepay request failed!");
        }

        //解密返回结果
        return result;
    }

}
