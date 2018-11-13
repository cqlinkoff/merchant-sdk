package com.client.sdk.request;

import com.alibaba.fastjson.JSONObject;
import com.client.exception.ApiException;
import com.client.sdk.dto.request.QueryRefBillNoRequest;
import com.client.utils.PayUtil.PayUtil;
import com.client.utils.StatusUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AccountCheckCommand {
    private static String serverUrl = "http://47.100.18.6:8200/dcpayCore/payBills/queryByOrderNo";
    private static Logger logger = LoggerFactory.getLogger(AccountCheckCommand.class);
    private static Long[] longs = {1525321877331L,1525322615843L};
    private static String merchantId = "10000000000003";
    private static String channel = "73088886094000";
    private  String private_key="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpRQRz9cwhgs0B511/qCU4eQXLDyL4TxJ2SGEcw3DK9EDoFQkxFgDmFQAwMa2jIKl/el0rom3tAQObQIG2UaNceV0y6h65UGr5rGYWCsUDTa0oc7ZIH2spceKJqneALXEYFedTvbIcuck+jguRp8jXBZyzJVLbcuoYAtm9cFYmKY1vNSMZlQdEKGFKNKcSKTG1Ee0ViL36hUNQNmPxM+azY86IrwZ/j6nkgSDK5tjO8o9LzZAxqMTKe+37zNE6VaIEEZQrEigzTahtVt//PbuMV5x54Y697JZ+LWMWt4ciYgYg3JnOh++KFsOYY48VEwL48XIy8sLKD3uRfEcRN7VXAgMBAAECggEAMvgbTSeYXgba4FXgq43gF08p1WkraIW203Mb+uNG1XYTDU84Q1WS07VDJz/uPw6Yu1AHOhpg61rA0UDtTg7rF+9bhvdEZRy3+ZJV8xEvHb2IBIRDQRhk1kf6pOV1slz8jvoZe/fE6C1xUarIBQX2zfhcpPz6JCZIIw7IrehjqH2f533V+guSm+iCaIhT6vUD5EfNpuam0E8GGUIPkdVfkRtykQa76yu1wpv2yFrBsrRsc8y9kGumJ1VFdaOVVX9d6LAIkipL3YNm1HvPBnktj64oM90YA6m3A/bFBOFqIrMwUBT71UIDmtM1grBnDzs3Banuvq2QPZUv4BI3g8AgIQKBgQDxEXQaKadM1pfj6JKW6kJBIz40wcqD+MionM3WP56GGuFn29Vq0H0vHwj2f/rTIklcFVYuoWYDOYIrMniRTt1I2WhwTj98Pcpb5asrkDdEaVlYQq7+R1bXJXUQKA9/JP8ppOqfg7HP/UCjuH3h/ak+y0ZKJGug1CQhds9ECZ1X6QKBgQCzwRM8XKTxnrCbDIy0WHWMe5HzPT6oi9gtulVr1jSpcXmO26NYQP34jIYHG2WFscVB8lhVRsTCaOXgbgpzBH99q6+wF9YzKUhmMO2V8c/MdXnsFHdYpUN+JpjQWvIKEFxcouXcyiaenkLN1m2oUoMwb46CABkxBRQlB7wXXv2bPwKBgBxNHkJMDZYZw29ASKVrDygyiQUMk0f3FyekcQ3sHiJEWZ4l0uJdY7T6gcTetYXACrjC0IFc9Wr/f2au4DS++3+n9njo1s8xOeacCgJtRe/EJncULRMxMOLFRP8GlPsqTsKG1/yuK1vtsX8HE9BKRWpX1wKxT+lrvmonVqH4Nv6xAoGAXTYBg4uG/MQNUFlxnRNB4Vcyl69qjnv13cCCCylIpZTyM+IxEdKh4AD+fzD1tB4667d/lrjbzvQWQArP4FS0x7X/pJC3wk/l+xfkG50I5D0GvCTgvlb0aLYbB/AhEpbpTiAqkhNBc38dpR9MPbyLytIOU9s5NPItQAaCwpu/ZoECgYEA7VVvx5Fn6O13gfl4MTLPBuG8jmOreak69eVcvYJeQ6ZP5BNx54mshr6KF+ck+3XBVQiD3IjV6V8lVBeH2OFUQ5+dDJWqdC+H8Y6X6loUa0V/q7vHwYNA11S3HeF/77Ybnq0zd7Cdk2J4ACV6IX7ChMglsvCdUMk5OgYUBaLjD2Y=";
    private  String mbr_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgUtjUwQCpo5c49BHhU+k+DU5XYA5Ww9+Jeql9J4IzvHoW1ChX2tDiBPwB+pJbrUE9EEw+jEuj9QQIKyQwBbYpyoNh0uJMsGJJRcuIJYEsznw4wzvU/q9U0OcYJfQ9Qu5xQ3dH2yk1CS4v2Ai1v+wngZ1t4hcvKW2Ccbyh4SGxVQHhOCC4JchFHgmsRsytjIzZHxOGMvzhRy2fjHcYMyGNpgqHBMHx4sTtIdrKZ52MQ+A/Vjj3iznXbLNRxz5PtFO/c2iX8l6FbYj6iMXmcUZlUv08g7H1+hLObVHbDBLML/DkwQwpxz26f9S6jgR7XBBD31+tc5Vlede7cYWDLmx9QIDAQAB";//面壁人平台公钥

    public AccountCheckCommand(){

    }

    public AccountCheckCommand(String mbr_public_key, String private_key){
        this.mbr_public_key = mbr_public_key;
        this.private_key = private_key;
    }

    //加密
    public  Map<String, String> getEncrypt(String body){
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
     * 解密返回data
     */
    public  String getDecrypt(String body){
        logger.info("begin Decrypt....");
        logger.info("the body is:"+body+"\r\n the request mbrPublic is: "+this.mbr_public_key+"\r\n the request merchantPrivate is: "+this.private_key);
        Map<String, String> mapData = JSONObject.parseObject(body, Map.class);
        if(!"200".equals(mapData.get("code"))){
            throw new ApiException(StatusUtil.ACCOUNTCHECK_REQUEST_FAILED, body);
        }
        String resultDecrypt = PayUtil.getDecrypt(mapData.get("data"), this.mbr_public_key, this.private_key);
        JSONObject resultObject = JSONObject.parseObject(resultDecrypt);
        if(!"200".equals(resultObject.getString("code"))){
            throw new ApiException(resultObject.getString("code"), resultObject.getString("message"));
        }
        String result = resultObject.getString("data");
        logger.info("the return data of Decrypt is:"+result);
        return result;
    }

    public static void main(String[] args) {
        AccountCheckCommand accountCheckCommand = new AccountCheckCommand();
        QueryRefBillNoRequest req = new QueryRefBillNoRequest();
        req.setOrderNo(longs);
        req.setMerchantId(merchantId);
        req.setChannel(channel);
        //加密
        Map<String, String> encryptMap = accountCheckCommand.getEncrypt(JSONObject.toJSONString(req));
        Map<String,String> doPostMap = new HashMap<>();
        doPostMap.put("url",serverUrl);
        doPostMap.put("params",encryptMap.get("body"));
        doPostMap.put("token",encryptMap.get("token"));
        //发送请求
        String doPostResult = accountCheckCommand.doPost(JSONObject.toJSONString(doPostMap));
        //解密数据
        String decryptResult = accountCheckCommand.getDecrypt(doPostResult);

        logger.info(decryptResult);
    }
}
