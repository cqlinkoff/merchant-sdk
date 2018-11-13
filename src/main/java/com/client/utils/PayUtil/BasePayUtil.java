package com.client.utils.PayUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockgod.common.utils.SecurityUtil;
import com.client.exception.ApiException;
import com.client.sdk.dto.response.BaseResponse;
import com.client.sdk.dto.response.PrePayResponse;
import com.client.utils.BeanUtil;
import com.client.utils.StatusUtil;
import com.client.utils.TokenUtil;
import com.client.utils.security.DCPEncryptor;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Pattern;

public class BasePayUtil {
    private static Logger logger = LoggerFactory.getLogger(BasePayUtil.class);
    //加密
    public static Map<String,String> getEncrypt(String body, String mbrPublic, String merchantPrivate) throws ApiException {
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
        //中间请求
    public static String doPost(String url,String params,String token) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("token", token);
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == 200) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                String var12 = jsonString;
                return var12;
            }

            logger.error("请求返回:" + state + "(" + url + ")");
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException var24) {
                    var24.printStackTrace();
                }
            }

            try {
                httpclient.close();
            } catch (IOException var23) {
                var23.printStackTrace();
            }

        }
        logger.info("http request failed!");
        return null;
    }
    //解密
    public static String getDecrypt(String body,String mbrPublic,String merchantPrivate) throws ApiException {
        Map<String,String> mapData = JSONObject.parseObject(body,Map.class);
        boolean b = DCPEncryptor.verifySignature(mapData.get("signature"),"RSA2",mapData.get("key"),mapData.get("iv"),mapData.get("cipher"),mbrPublic);
        if (b){
            //解密
            byte[] responseBodyByte = DCPEncryptor.decrypt(mapData.get("key"),mapData.get("iv"),mapData.get("cipher"),merchantPrivate);
            String bodyString = "";
            try{
                bodyString = new String(responseBodyByte,"UTF-8");
                return bodyString;
            }catch (Exception e){
                throw new ApiException(StatusUtil.UNSUPPORTED_ENCODING, "byte to string failed");
            }
        }else{
            //验签失败
            throw new ApiException(StatusUtil.SIGNATURE_FAILED, "signature failed");
        }
    }

}
