package com.client.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.client.exception.ApiException;
import com.client.sdk.base.BaseClass;
import com.client.sdk.dto.header.Device;
import com.client.sdk.dto.header.Header;
import com.client.sdk.dto.response.BaseResponse;
import com.client.utils.security.DCPEncryptor;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

/**
 * Created by Ethan.Yuan on 2018/5/4.
 */
public class TokenUtil extends BaseClass{

    public static String getToken(String DeviceId, String version,String channel, String signature, String merchantId) throws UnsupportedEncodingException {
        Device device = new Device();
        device.setDeviceId(DeviceId);
        device.setAppVersion(version);
        device.setChannel(channel);
        Header header = new Header();
        header.setDevice(device);
        header.setSignType("RSA2");
        header.setSignature(signature);
        header.setMerchantId(merchantId);
        header.setApiVersion("0.1");
        header.setTimestamp(new Date().getTime());
        String headerString = JSONObject.toJSONString(header);
        String token = "";

        token = Base64.getEncoder().encodeToString(headerString.getBytes("UTF-8"));

        return token;
    }

    public static ResultUtil decryptResponse(String response, String merchantPrivate, String mbrPublic){
        Map<String,String> resMap = JSONObject.toJavaObject(JSON.parseObject(response),Map.class);

        if (resMap.get("code").equals(StatusUtil.SUCCESS)){
            String mapDataString = (String) resMap.get("data");
            Map<String,String> mapData = JSONObject.toJavaObject(JSON.parseObject(mapDataString),Map.class);
            boolean b = DCPEncryptor.verifySignature(mapData.get("signature"),"RSA2",mapData.get("key"),mapData.get("iv"),mapData.get("cipher"),mbrPublic);
            if (b){
                //解密
                byte[] responseBodyByte = DCPEncryptor.decrypt(mapData.get("key"),mapData.get("iv"),mapData.get("cipher"),merchantPrivate);
                String bodyString = "";
                try{
                    bodyString = new String(responseBodyByte,"UTF-8");
                }catch (Exception e){
                    return failed(StatusUtil.PRE_PAY_REQUEST_FAILED);
                }
                BaseResponse baseResponse = JSONObject.toJavaObject(JSON.parseObject(bodyString),BaseResponse.class);
                if (baseResponse.getCode().equals(StatusUtil.SUCCESS)){
                    try{
                        return success(baseResponse.getData());
                    }catch (ApiException e){
                        return failed(e.getMessage());
                    }catch (Exception e){
                        return failed(e.getMessage());
                    }
                }else{
                    //支付core返回失败
                    return failed(StatusUtil.PRE_PAY_REQUEST_FAILED);
                }
            }else{
                //验签失败
                return failed(StatusUtil.SIGNATURE_FAILED);
            }
        }else{
            //支付core网络请求失败
            String msg = null;
            try{
                msg = resMap.get("message");
                if(msg == null){
                    msg = resMap.get("msg");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return failed(resMap.get("code"), msg);
        }
    }
}
