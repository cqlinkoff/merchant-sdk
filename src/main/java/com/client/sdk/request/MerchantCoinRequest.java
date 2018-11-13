package com.client.sdk.request;

import com.alibaba.fastjson.JSONObject;
import com.client.config.Config;
import com.client.sdk.base.BaseClass;
import com.client.sdk.dto.request.QueryCoinRequest;
import com.client.sdk.dto.response.MerchantCoinResponse;
import com.client.utils.HttpRequest;
import com.client.utils.ResultUtil;
import com.client.utils.StatusUtil;
import com.client.utils.TokenUtil;
import com.client.utils.security.DCPEncryptor;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class MerchantCoinRequest extends BaseClass{

    private String serverUrl = HttpRequest.baseUrl + "client/merchant/withdraw/queryWithdrawCoin";

    public MerchantCoinRequest(String serverUrl){
        this.serverUrl = serverUrl;
    }

    public ResultUtil queryCoinList(QueryCoinRequest queryCoinRequest , String merchantPrivate, String mbrPublicKey){
        //1. 私钥加密aeskey
        String body = JSONObject.toJSONString(queryCoinRequest);
        Map<String,String> bodyEncryptMap = DCPEncryptor.encrypt(body,mbrPublicKey,merchantPrivate);
        //2.构造消息头
        String token = null;
        try {
            token = TokenUtil.getToken("app", "0.1",queryCoinRequest.getChannel().toString(), bodyEncryptMap.get("signature"), queryCoinRequest.getMerchantId());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        bodyEncryptMap.remove("signature");
        //发送
        String response = "";
        try{
            response = HttpRequest.doPost(serverUrl,JSONObject.toJSONString(bodyEncryptMap),token);
        }catch (Exception e){
            return failed(StatusUtil.POST_FAILED);
        }
        return TokenUtil.decryptResponse(response, merchantPrivate, mbrPublicKey);
    }

}
