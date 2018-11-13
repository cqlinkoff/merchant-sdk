package com.client.sdk.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockgod.common.utils.SecurityUtil;
import com.client.exception.ApiException;
import com.client.sdk.dto.request.PrePayRequest;
import com.client.sdk.dto.response.BaseResponse;
import com.client.sdk.dto.response.PrePayResponse;
import com.client.utils.BeanUtil;
import com.client.utils.PayUtil.PayUtil;
import com.client.utils.StatusUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

public class PrePayCommand {

//    private static Logger logger = LoggerFactory.getLogger(PrePayCommand.class);
    private static String amount = "1";
    private static String coinId = "5773162675373";
//    private static String merchantId = "8266163269740";
    private static String merchantId = "10000000000003";
    private static String refBizNo = String.valueOf(System.currentTimeMillis());
//    private static String channel = "8266164582560";
    private static String channel = "73088886094000";
    private static String prepayUrl = "http://192.168.1.33:9900/dcpayCore/payBills/prepay";
    private String private_key="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpRQRz9cwhgs0B511/qCU4eQXLDyL4TxJ2SGEcw3DK9EDoFQkxFgDmFQAwMa2jIKl/el0rom3tAQObQIG2UaNceV0y6h65UGr5rGYWCsUDTa0oc7ZIH2spceKJqneALXEYFedTvbIcuck+jguRp8jXBZyzJVLbcuoYAtm9cFYmKY1vNSMZlQdEKGFKNKcSKTG1Ee0ViL36hUNQNmPxM+azY86IrwZ/j6nkgSDK5tjO8o9LzZAxqMTKe+37zNE6VaIEEZQrEigzTahtVt//PbuMV5x54Y697JZ+LWMWt4ciYgYg3JnOh++KFsOYY48VEwL48XIy8sLKD3uRfEcRN7VXAgMBAAECggEAMvgbTSeYXgba4FXgq43gF08p1WkraIW203Mb+uNG1XYTDU84Q1WS07VDJz/uPw6Yu1AHOhpg61rA0UDtTg7rF+9bhvdEZRy3+ZJV8xEvHb2IBIRDQRhk1kf6pOV1slz8jvoZe/fE6C1xUarIBQX2zfhcpPz6JCZIIw7IrehjqH2f533V+guSm+iCaIhT6vUD5EfNpuam0E8GGUIPkdVfkRtykQa76yu1wpv2yFrBsrRsc8y9kGumJ1VFdaOVVX9d6LAIkipL3YNm1HvPBnktj64oM90YA6m3A/bFBOFqIrMwUBT71UIDmtM1grBnDzs3Banuvq2QPZUv4BI3g8AgIQKBgQDxEXQaKadM1pfj6JKW6kJBIz40wcqD+MionM3WP56GGuFn29Vq0H0vHwj2f/rTIklcFVYuoWYDOYIrMniRTt1I2WhwTj98Pcpb5asrkDdEaVlYQq7+R1bXJXUQKA9/JP8ppOqfg7HP/UCjuH3h/ak+y0ZKJGug1CQhds9ECZ1X6QKBgQCzwRM8XKTxnrCbDIy0WHWMe5HzPT6oi9gtulVr1jSpcXmO26NYQP34jIYHG2WFscVB8lhVRsTCaOXgbgpzBH99q6+wF9YzKUhmMO2V8c/MdXnsFHdYpUN+JpjQWvIKEFxcouXcyiaenkLN1m2oUoMwb46CABkxBRQlB7wXXv2bPwKBgBxNHkJMDZYZw29ASKVrDygyiQUMk0f3FyekcQ3sHiJEWZ4l0uJdY7T6gcTetYXACrjC0IFc9Wr/f2au4DS++3+n9njo1s8xOeacCgJtRe/EJncULRMxMOLFRP8GlPsqTsKG1/yuK1vtsX8HE9BKRWpX1wKxT+lrvmonVqH4Nv6xAoGAXTYBg4uG/MQNUFlxnRNB4Vcyl69qjnv13cCCCylIpZTyM+IxEdKh4AD+fzD1tB4667d/lrjbzvQWQArP4FS0x7X/pJC3wk/l+xfkG50I5D0GvCTgvlb0aLYbB/AhEpbpTiAqkhNBc38dpR9MPbyLytIOU9s5NPItQAaCwpu/ZoECgYEA7VVvx5Fn6O13gfl4MTLPBuG8jmOreak69eVcvYJeQ6ZP5BNx54mshr6KF+ck+3XBVQiD3IjV6V8lVBeH2OFUQ5+dDJWqdC+H8Y6X6loUa0V/q7vHwYNA11S3HeF/77Ybnq0zd7Cdk2J4ACV6IX7ChMglsvCdUMk5OgYUBaLjD2Y=";
//    private String private_key="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCM0wuMJ5Od7z+nvds/C2vpyS6x7h6lUsrmGH9ANZoyIYwIzN4QWQuL940DPNF9t0SbDtffqbshwEjp3bWynQpC/EPLz4Od0zzvbf58pvfXaIh8nI8r3ldMTAmmtT64oZK2xrMwTJUWF5En1MFpCSr68yU4/X+2Rp1NpE2vimp34H2msxCoYi91FdBh5XcxjQPM4j2gGdY3kn2hcTSx5beKITb2bBW+nMKObT7JyTKFMFVUYQ0Yt/xghCK8Cz3dIAr20rLBFwJ4/suJlUHF6XadVxBFCxvdHlOEP1+02tEGhPb/TjMOSZO8zKXH6PaMyN8yrqafcMkEfPkZKUEWVjLXAgMBAAECggEALu7/gDyIdDCSlIMwGPjjhE0qpN5plEvdl4Y+ktXKiD6p/xzYPbaPpJxigNu6Lh3wXYU66S4WUg9VvavIrLit/Nk6dyi4AteuDVYpZL35B++s3WsIRGSbz6/f35G0T7B5xzS977D2VyCPnijINQt7KZvzoeTKtaMfNgs2YPTOuxi1TtnroDkqjhHplj4P/SHjalBI2ipXWyGQFgDKaexQafbESd+aNBR3d8PgOlouPBdlVJ5PZg9+LuWlLj+l7MqQZfreWN8YpVPYi+OreT+95j/C7QEDv/6kYQEqME03FZfAUCB2WI35aABuzzwcwzGb47+xnBH/+6M17hXULv+DsQKBgQDSTKw3AVwR+QqR3GTcocQGtWmxqKFFWcp+mri0HV8o2yBowhJVLsuVF+nyAyZSR1bZyJI+e/EOaoLFyEHiwtLAAr0DnNT5ZMzmqArUxqBMdRpMUbSdCFyTfD4adlHfFhVTH6OD2YpcUTFgtYEBYJjT8tpdvxM8gvYKzm2VcI0BrQKBgQCrbVnrd0nfy+z2XjgfCWAbWgwMOyGg5/2jeHJQloJzgxuMfdzzdMIcOyTzHHMAnExHw74R6Es0ZZY8iNaxGlfTy8xPS0s64t5+AFD6qhAwfIMTLCLkjbPMXYv7FT0e0cPvcvwc9/vh6Faf9PQKpxu7NKEYzTH4pNa6tNSJz/u/EwKBgQCtPlac1bToZXYMpTg9/dGECUzx+04n2ImVdJZ2Yr/y41m2kzIRFITH5TRnl6qpG3Xz4WSArw/tZVcn4gpM6Vw+q8LUSA23ChA2b/bERIDnm4y3lJOqzameIjrzB8f4dLPKj4Tf6IUv+f8ogT2uScp8yFKV6gPJ1MUmL9pcwqSW3QKBgBUdOt4Nq0OtUlpl8HOiK/wycxlujw3KhSsFQs86OocPtuzu86/oyZ/AiSSIdIG2vo54RYjfG6Qwfvilwgu+OuMTTu7VT0bJeq36S/8Twzq1m09ZOFL+QQ8C1qLuUsDhBUkQ2IyY3qVMgJ7jBMvrGTdNnCn53BXL98a3TxZKT6+RAoGAFrWw5xS6Huo38hV5ryoV+wOrAh11wA9ff5cuvuzAELWafmMoCynUwiZYcTlxeowe8cwByhoLQYv6bDezoa7Jls4yDDh06Ajbo3EhkI114BOVi6qgbZh3bcwRjCJjoUhI9HTycmzDxGS3KKM7/R5ObX6jKLClZaMlUiEsd0TyGoQ=";
    private String mbr_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgUtjUwQCpo5c49BHhU+k+DU5XYA5Ww9+Jeql9J4IzvHoW1ChX2tDiBPwB+pJbrUE9EEw+jEuj9QQIKyQwBbYpyoNh0uJMsGJJRcuIJYEsznw4wzvU/q9U0OcYJfQ9Qu5xQ3dH2yk1CS4v2Ai1v+wngZ1t4hcvKW2Ccbyh4SGxVQHhOCC4JchFHgmsRsytjIzZHxOGMvzhRy2fjHcYMyGNpgqHBMHx4sTtIdrKZ52MQ+A/Vjj3iznXbLNRxz5PtFO/c2iX8l6FbYj6iMXmcUZlUv08g7H1+hLObVHbDBLML/DkwQwpxz26f9S6jgR7XBBD31+tc5Vlede7cYWDLmx9QIDAQAB";//面壁人平台公钥



    private final static String[] requiredArray = {"amount", "coinId", "merchantId", "payBillNo", "refBizNo", "toAddr"};

    public PrePayCommand(){

    }

    public PrePayCommand(String mbr_public_key, String private_key){
        this.mbr_public_key = mbr_public_key;
        this.private_key = private_key;
    }


    /**
     * 加密
     */
    public Map<String, String> getEncrypt(String body) throws ApiException{
        Map<String, String> result = PayUtil.getEncrypt(body, mbr_public_key, private_key);
        return result;
    }

    /**
     * 发送请求
     */
    public static String doPost(String params) throws ApiException {
        Map<String,String> mapParams = JSONObject.parseObject(params,Map.class);
        return PayUtil.doPost(mapParams);
    }

    /**
     * 解密
     */
    public String getDecrypt(String body) throws ApiException{
//        logger.info("begin Decrypt....");
//        logger.info("the body is:"+body+"\r\n the request mbrPublic is: "+this.mbr_public_key+"\r\n the request merchantPrivate is: "+this.private_key);
        Map<String, String> mapData = JSONObject.parseObject(body, Map.class);
        if(!"200".equals(mapData.get("code"))){
            throw new ApiException(StatusUtil.PRE_PAY_REQUEST_FAILED, body);
        }
        String result = PayUtil.getDecrypt(mapData.get("data"), this.mbr_public_key, this.private_key);
//        logger.info("the return data of Decrypt is:"+result);
        return result;
    }

    public String getOrderInfo(String bodyString){
        BaseResponse<PrePayResponse> baseResponse = JSONObject.toJavaObject(JSON.parseObject(bodyString),BaseResponse.class);
        if (baseResponse.getCode().equals(StatusUtil.SUCCESS)){
            try{
                //拼装属性字符串，加签名
                String orderInfo = getResponseString(baseResponse.getData(), this.private_key);
                return orderInfo;
            }catch (Exception e){
                throw new ApiException(StatusUtil.PRE_PAY_REQUEST_FAILED, e.getMessage());
            }
        }else{
            //支付core返回失败
            throw new ApiException(baseResponse.getCode(), baseResponse.getMessage());
        }
    }

    private static String getResponseString(Object response, String merchantPrivate) throws Exception{
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
        String preSignString = getNoNullString(signSb.toString());
        return preSignString + "&sign=" + SecurityUtil.RsaUtil.sign(preSignString, merchantPrivate, true, "UTF-8");
    }

    /**
     * 去除值为空的字符串
     * @param str
     * @return
     */
    private static String getNoNullString(String str){
        //用正则将aaa=&这样的字符串替换为空，保证等号后面都是有值的
        String signString = Pattern.compile("[a-zA-Z_0-9]*=&").matcher(str).replaceAll("");
        return signString.endsWith("&") ? signString.substring(0, signString.length() - 1) : signString;
    }




}
