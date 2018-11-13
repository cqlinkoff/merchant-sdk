package com.client.sdk.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.client.config.Config;
import com.client.exception.ApiException;
import com.client.sdk.base.BaseClass;
import com.client.sdk.dto.StandardEnum;
import com.client.sdk.dto.eth.BGETHTXHelper;
import com.client.sdk.dto.header.Device;
import com.client.sdk.dto.header.Header;
import com.client.sdk.dto.request.CreateTxRequest;
import com.client.sdk.dto.request.WithdrawRequest;
import com.client.sdk.dto.response.BaseResponse;
import com.client.sdk.dto.response.CreateTxResponse;
import com.client.utils.*;
import com.client.utils.security.DCPEncryptor;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.spongycastle.asn1.bc.ObjectData;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.Wallet;
import org.web3j.tx.ChainId;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.*;

public class TransactionRequest  extends BaseClass{

    private String serverUrl = HttpRequest.baseUrl + "client/tx/txCreate";

    private String withdrawUrl = HttpRequest.baseUrl + "dcpayCore/payBills/withdraw";

    public TransactionRequest(){

    }

    public TransactionRequest(String createTxUrl, String withdrawUrl){
        this.serverUrl = createTxUrl;
        this.withdrawUrl = withdrawUrl;
    }

    public ResultUtil createTx(CreateTxRequest createTxRequest, String merchantPrivate, String mbrPublicKey){
        //1. 私钥加密aeskey
        String body = JSONObject.toJSONString(createTxRequest);
        Map<String,String> bodyEncryptMap = DCPEncryptor.encrypt(body,mbrPublicKey,merchantPrivate);
        //2.构造消息头
        String token = null;
        try {
            token = TokenUtil.getToken("app", "0.1",createTxRequest.getChannel(), bodyEncryptMap.get("signature"), createTxRequest.getMerchantId());
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


    public ResultUtil sendWithDraw(WithdrawRequest withdrawRequest, String merchantId, String merchantPrivate, String mbrPublic){
        Map<String,String> bodyEncryptMap = DCPEncryptor.encrypt(JSONObject.toJSONString(withdrawRequest),mbrPublic,merchantPrivate);
        String token = null;
        try {
            token = TokenUtil.getToken("app", "0.1",withdrawRequest.getChannel(), bodyEncryptMap.get("signature"), merchantId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        bodyEncryptMap.remove("signature");
        String response = "";
        try {
            response = HttpRequest.doPost(withdrawUrl, JSONObject.toJSONString(bodyEncryptMap), token);
        }catch (Exception e){
            return failed(StatusUtil.POST_FAILED);
        }
        return TokenUtil.decryptResponse(response, merchantPrivate, mbrPublic);
    }





}
