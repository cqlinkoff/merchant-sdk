package com.client.sdk.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockgod.common.utils.SecurityUtil;
import com.client.exception.ApiException;
import com.client.sdk.base.BaseClass;
import com.client.sdk.dto.header.Device;
import com.client.sdk.dto.header.Header;
import com.client.sdk.dto.request.PrePayRequest;
import com.client.sdk.dto.response.BaseResponse;
import com.client.sdk.dto.response.PrePayResponse;
import com.client.utils.*;
import com.client.utils.security.DCPEncryptor;
import com.client.utils.security.rsa.DCPRSA;
import org.springframework.beans.BeanUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ethan.Yuan
 */
public class PrePayHttpRequest extends BaseClass{


    private String serverUrl = HttpRequest.baseUrl + "dcpayCore/payBills/prepay";

    private final static String[] requiredArray = {"amount", "coinId", "merchantId", "payBillNo", "refBizNo", "toAddr"};

    public PrePayHttpRequest(String url){
        this.serverUrl = url;
    }


    /**
     * 预支付
     */
    public ResultUtil sendPrePay(PrePayRequest prePayRequest, String merchantPrivate, String mbrPublicKey){

        //1. 私钥加密aeskey
        String body = JSONObject.toJSONString(prePayRequest);
        Map<String,String> bodyEncryptMap = DCPEncryptor.encrypt(body,mbrPublicKey,merchantPrivate);

        //2.构造消息头
        String token = null;
        try {
            token = TokenUtil.getToken("app", "0.1","73088886094000", bodyEncryptMap.get("signature"),prePayRequest.getMerchantId());
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
        ResultUtil result = TokenUtil.decryptResponse(response, merchantPrivate, mbrPublicKey);
        if(result.getCode().equals(StatusUtil.SUCCESS)){
            try{
                //拼装属性字符串，加签名
                String orderInfo = getResponseString(result.getData(), merchantPrivate);
                return success(orderInfo);
            }catch (Exception e){
                return failed(StatusUtil.SIGNATURE_FAILED);
            }
        }else{
            return result;
        }
    }

    private String getResponseString(Object response, String merchantPrivate) throws Exception{
        //将response和request的属性都转换为Map
        Map<String, Object> map = null;
        if(response instanceof PrePayResponse){
            map = BeanUtil.getKeyAndValue(response);
        }else{
            //JSONObject
            map = ((JSONObject)response).getInnerMap();
        }
        //0. 检查必要参数
        for(String key : requiredArray){
            if(null == map.get(key)){
                throw  new ApiException(StatusUtil.PARAMETER_INVALID, key + " is required!");
            }
        }
        //1. 组装明文字符串
        List<String> parameterList = new ArrayList<String>(map.size());
        //1.1 组装参数list
        for(String key : map.keySet()){
            parameterList.add(key);
        }
        //1.2 参数排序
        Collections.sort(parameterList);
        //1.3 组装明文字符串
        StringBuilder sb = new StringBuilder();
        for(String key : parameterList){
            Object value = map.get(key);
            sb.append(key).append("=").append(value == null ? "" : value.toString()).append("&");
        }
        //2. 去除明文字符串中为空的部分后签名
        StringBuilder signSb = new StringBuilder(sb.toString());
        String preSignString = this.getNoNullString(signSb.toString());
        return preSignString + "&sign=" + SecurityUtil.RsaUtil.sign(preSignString, merchantPrivate, true, "UTF-8");
    }

    /**
     * 去除值为空的字符串
     * @param str
     * @return
     */
    private String getNoNullString(String str){
        //用正则将aaa=&这样的字符串替换为空，保证等号后面都是有值的
        String signString = Pattern.compile("[a-zA-Z_0-9]*=&").matcher(str).replaceAll("");
        return signString.endsWith("&") ? signString.substring(0, signString.length() - 1) : signString;
    }

}
