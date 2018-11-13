package com.client.sdk.callback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockgod.common.utils.security.DCPEncryptor;
import com.client.exception.ApiException;
import com.client.sdk.base.BaseClass;
import com.client.sdk.dto.response.BaseResponse;
import com.client.sdk.dto.response.PayNotifyResponse;
import com.client.sdk.dto.response.WithdrawNotifyResponse;
import com.client.utils.BeanUtil;
import com.client.utils.ResultUtil;
import com.client.utils.StatusUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class PayCallBack extends BaseClass{

    private static Logger logger = LoggerFactory.getLogger(PayCallBack.class);

    public ResultUtil httpPayCallBack(String mbrPublicKey, String merchantPrivate, HttpServletRequest httpServletRequest) throws Exception {
        String requestString = IOUtils.toString(httpServletRequest.getInputStream(),"UTF-8");
        logger.info("服务器支付回调加密数据->{}",requestString);
        Map<String,String> mapData = JSONObject.toJavaObject(JSON.parseObject(requestString),Map.class);
        boolean b = DCPEncryptor.verifySignature(mapData.get("signature"),"RSA2",mapData.get("key"),mapData.get("iv"),mapData.get("cipher"),mbrPublicKey);
        if (b) {

            byte[] responseBodyByte = DCPEncryptor.decrypt(mapData.get("key"),mapData.get("iv"),mapData.get("cipher"),merchantPrivate);
            String bodyString = new String(responseBodyByte,"UTF-8");
            logger.info("服务器支付回调解密数据->{}",bodyString);
            JSONObject object = JSONObject.parseObject(bodyString);
            Map<String, Object> map = object.getInnerMap();
            return super.success(map);
        }else{
            return super.failed(StatusUtil.SIGNATURE_FAILED);
        }
    }


    public static WithdrawNotifyResponse httpWithdrawCallBack(String mbrPublicKey, String merchantPrivate, HttpServletRequest httpServletRequest) throws Exception {
        String requestString = IOUtils.toString(httpServletRequest.getInputStream(),"UTF-8");
        logger.info("服务器提现回调加密数据->{}",requestString);
        Map<String,String> mapData = JSONObject.toJavaObject(JSON.parseObject(requestString),Map.class);
        boolean b = DCPEncryptor.verifySignature(mapData.get("signature"),"RSA2",mapData.get("key"),mapData.get("iv"),mapData.get("cipher"),mbrPublicKey);
        if (b) {

            byte[] responseBodyByte = DCPEncryptor.decrypt(mapData.get("key"),mapData.get("iv"),mapData.get("cipher"),merchantPrivate);
            String bodyString = new String(responseBodyByte,"UTF-8");
            logger.info("服务器提现回调解密数据->{}",bodyString);
            BaseResponse<WithdrawNotifyResponse> baseResponse = JSONObject.toJavaObject(JSON.parseObject(bodyString),BaseResponse.class);
            if (baseResponse.getData().equals("200")){
                return baseResponse.getData();
            }else{

                throw new ApiException("99991","数据解密失败");
            }
        }else{
            throw new ApiException("99999","签名验证失败");
        }
    }
}
