package com.client.utils;

import com.alibaba.fastjson.JSONObject;
import com.client.exception.ApiException;
import com.client.utils.security.DCPEncryptor;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TestUtil {
    //加密
    public Map<String,String> getEncrypt(String body, String mbrPublic, String merchantPrivate) throws ApiException {
        Map<String,String> mapParam = JSONObject.parseObject(body,Map.class);
        //1.加密
        Map<String,String> bodyEncryptMap = DCPEncryptor.encrypt(body,mbrPublic,merchantPrivate);

        //2.构造消息头
        try{
            String token = TokenUtil.getToken("app", "0.1",mapParam.get("channel"), bodyEncryptMap.get("signature"),mapParam.get("merchantId"));
            bodyEncryptMap.remove("signature");

            //加密数据和消息头
            Map<String,String> result = new HashMap<>();
            result.put("body",JSONObject.toJSONString(bodyEncryptMap));
            result.put("token",token);
            return result;
        }catch (UnsupportedEncodingException e){
            throw new ApiException(StatusUtil.UNSUPPORTED_ENCODING, e.getMessage());
        }

    }


}
