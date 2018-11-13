package com.client.sdk.request;

import com.alibaba.fastjson.JSONObject;
import com.client.exception.ApiException;
import com.client.sdk.base.BaseClass;
import com.client.sdk.dto.request.CreateTxRequest;
import com.client.sdk.dto.response.MerchantCoinResponse;
import com.client.utils.PayUtil.PayUtil;
import com.client.utils.PayUtil.TxUtil;
import com.client.utils.StatusUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TransactionCommand extends BaseClass {
   /* private static String coinListUrl = "http://47.100.18.6:9900/client/merchant/withdraw/queryWithdrawCoin";
    private static String txCreateUrl = "http://47.100.18.6:9900/client/tx/txCreate";
    private static String txUrl = "http://47.100.18.6:9900/dcpayCore/dcpayCore/payBills/withdraw";*/

//    private static Logger logger = LoggerFactory.getLogger(TransactionCommand.class);

    private static final String merchantCoinUrl = "http://47.100.18.6:9900/client/merchant/withdraw/queryWithdrawCoin";
    private static final String createTxUrl = "http://47.100.18.6:9900/client/tx/txCreate";
    private static final String channel_default = "73088886094000";
    private static String withdrawUrl = "http://47.100.18.6:9900/dcpayCore/payBills/withdraw";
    private static String coinListUrl = "https://api.cospay.io/client/merchant/withdraw/queryWithdrawCoin";
    private static String txCreateUrl = "https://api.cospay.io/client/tx/txCreate";
    private static String getOrderUrl = "http://47.100.18.6:9900/dcpayCore/payBills/queryByOrderNo";

    private static String merchantId = "8266163269740";
    private static String channel = "8266164582560";
    private static String coinId = "5773162675373";
    private static String amount = "1";
    private static String addressTo = "0xc2606bd69171c68b891b8479a86be341c43514ba";

    private String mbr_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgUtjUwQCpo5c49BHhU+k+DU5XYA5Ww9+Jeql9J4IzvHoW1ChX2tDiBPwB+pJbrUE9EEw+jEuj9QQIKyQwBbYpyoNh0uJMsGJJRcuIJYEsznw4wzvU/q9U0OcYJfQ9Qu5xQ3dH2yk1CS4v2Ai1v+wngZ1t4hcvKW2Ccbyh4SGxVQHhOCC4JchFHgmsRsytjIzZHxOGMvzhRy2fjHcYMyGNpgqHBMHx4sTtIdrKZ52MQ+A/Vjj3iznXbLNRxz5PtFO/c2iX8l6FbYj6iMXmcUZlUv08g7H1+hLObVHbDBLML/DkwQwpxz26f9S6jgR7XBBD31+tc5Vlede7cYWDLmx9QIDAQAB";
    public String private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCM0wuMJ5Od7z+nvds/C2vpyS6x7h6lUsrmGH9ANZoyIYwIzN4QWQuL940DPNF9t0SbDtffqbshwEjp3bWynQpC/EPLz4Od0zzvbf58pvfXaIh8nI8r3ldMTAmmtT64oZK2xrMwTJUWF5En1MFpCSr68yU4/X+2Rp1NpE2vimp34H2msxCoYi91FdBh5XcxjQPM4j2gGdY3kn2hcTSx5beKITb2bBW+nMKObT7JyTKFMFVUYQ0Yt/xghCK8Cz3dIAr20rLBFwJ4/suJlUHF6XadVxBFCxvdHlOEP1+02tEGhPb/TjMOSZO8zKXH6PaMyN8yrqafcMkEfPkZKUEWVjLXAgMBAAECggEALu7/gDyIdDCSlIMwGPjjhE0qpN5plEvdl4Y+ktXKiD6p/xzYPbaPpJxigNu6Lh3wXYU66S4WUg9VvavIrLit/Nk6dyi4AteuDVYpZL35B++s3WsIRGSbz6/f35G0T7B5xzS977D2VyCPnijINQt7KZvzoeTKtaMfNgs2YPTOuxi1TtnroDkqjhHplj4P/SHjalBI2ipXWyGQFgDKaexQafbESd+aNBR3d8PgOlouPBdlVJ5PZg9+LuWlLj+l7MqQZfreWN8YpVPYi+OreT+95j/C7QEDv/6kYQEqME03FZfAUCB2WI35aABuzzwcwzGb47+xnBH/+6M17hXULv+DsQKBgQDSTKw3AVwR+QqR3GTcocQGtWmxqKFFWcp+mri0HV8o2yBowhJVLsuVF+nyAyZSR1bZyJI+e/EOaoLFyEHiwtLAAr0DnNT5ZMzmqArUxqBMdRpMUbSdCFyTfD4adlHfFhVTH6OD2YpcUTFgtYEBYJjT8tpdvxM8gvYKzm2VcI0BrQKBgQCrbVnrd0nfy+z2XjgfCWAbWgwMOyGg5/2jeHJQloJzgxuMfdzzdMIcOyTzHHMAnExHw74R6Es0ZZY8iNaxGlfTy8xPS0s64t5+AFD6qhAwfIMTLCLkjbPMXYv7FT0e0cPvcvwc9/vh6Faf9PQKpxu7NKEYzTH4pNa6tNSJz/u/EwKBgQCtPlac1bToZXYMpTg9/dGECUzx+04n2ImVdJZ2Yr/y41m2kzIRFITH5TRnl6qpG3Xz4WSArw/tZVcn4gpM6Vw+q8LUSA23ChA2b/bERIDnm4y3lJOqzameIjrzB8f4dLPKj4Tf6IUv+f8ogT2uScp8yFKV6gPJ1MUmL9pcwqSW3QKBgBUdOt4Nq0OtUlpl8HOiK/wycxlujw3KhSsFQs86OocPtuzu86/oyZ/AiSSIdIG2vo54RYjfG6Qwfvilwgu+OuMTTu7VT0bJeq36S/8Twzq1m09ZOFL+QQ8C1qLuUsDhBUkQ2IyY3qVMgJ7jBMvrGTdNnCn53BXL98a3TxZKT6+RAoGAFrWw5xS6Huo38hV5ryoV+wOrAh11wA9ff5cuvuzAELWafmMoCynUwiZYcTlxeowe8cwByhoLQYv6bDezoa7Jls4yDDh06Ajbo3EhkI114BOVi6qgbZh3bcwRjCJjoUhI9HTycmzDxGS3KKM7/R5ObX6jKLClZaMlUiEsd0TyGoQ=";
    public String private_key_hex = "7820F3DF38FB27D46E1128FA45F4080F572695D95C7363A2D3E245E0F844317D";


    public TransactionCommand() {

    }

    public TransactionCommand(String mbr_public_key, String private_key, String private_key_hex) {
        this.mbr_public_key = mbr_public_key;
        this.private_key = private_key;
        this.private_key_hex = private_key_hex;
    }

    /**
     * 加密
     */
    public Map<String, String> getEncrypt(String body) throws ApiException {
        Map<String, String> result = PayUtil.getEncrypt(body, mbr_public_key, private_key);
        return result;
    }

    /**
     * 发送请求
     */
    public static String doPost(String params) throws ApiException {
        Map<String, String> mapParams = JSONObject.parseObject(params, Map.class);
        return PayUtil.doPost(mapParams);
    }

    /**
     * 解密
     */
    public String getDecrypt(String body) throws ApiException {
//        logger.info("begin Decrypt....");
//        logger.info("the body is:" + body + "\r\n the request mbrPublic is: " + this.mbr_public_key + "\r\n the request merchantPrivate is: " + this.private_key);
        Map<String, String> mapData = JSONObject.parseObject(body, Map.class);
        if (!"200".equals(mapData.get("code"))) {
            throw new ApiException(StatusUtil.WITHDRAW_REQUEST_FAILED, body);
        }
        String resultDecrypt = PayUtil.getDecrypt(mapData.get("data"), this.mbr_public_key, this.private_key);
        JSONObject resultObject = JSONObject.parseObject(resultDecrypt);
        if (!"200".equals(resultObject.getString("code"))) {
            throw new ApiException(resultObject.getString("code"), resultObject.getString("message"));
        }
        String result = resultObject.getString("data");
//        logger.info("the return data of Decrypt is:" + result);
        return result;
    }

    public Map<String, String> getCoin(Map<String, String> coinQueryParams) {
        Map<String, String> queryCoinRequest = new HashMap<String, String>();
        queryCoinRequest.put("merchantId", coinQueryParams.get("merchantId"));
        queryCoinRequest.put("channel", coinQueryParams.get("channel"));
        Map<String, String> resultEncryptData = this.getEncrypt(JSONObject.toJSONString(queryCoinRequest));
        //加密成功后，发送查询币列表请求
        Map<String, String> coinListPost = new HashMap<>();
        coinListPost.put("url", coinQueryParams.get("coinListUrl"));
        coinListPost.put("params", resultEncryptData.get("body"));
        coinListPost.put("token", resultEncryptData.get("token"));
        String resultCoinList = PayUtil.doPost(coinListPost);
//        logger.info(resultCoinList);
        //请求成功后，解密得到币列表
        String resultDecrypt = this.getDecrypt(resultCoinList);
//        logger.info("resultDecrypt:" + resultDecrypt);

        String coin = null;
        //查询到币列表后
        Map<String, String> searchCoinMap = new HashMap<>();
        searchCoinMap.put("coinId", coinQueryParams.get("coinId"));
        searchCoinMap.put("data", resultDecrypt);
        //根据币种号查询对应的币种信息
        coin = searchForCoin(JSONObject.toJSONString(searchCoinMap));
//        logger.info("coin:" + coin);
        return coin == null ? null : JSONObject.toJavaObject(JSONObject.parseObject(coin), Map.class);
    }


    /**
     * 查找对应的币种
     */
    public static String searchForCoin(String params) {
        JSONObject mapTxParams = JSONObject.parseObject(params);
        MerchantCoinResponse coin = TxUtil.searchForCoin(mapTxParams.getString("coinId"), mapTxParams.getJSONArray("data").toJavaList(JSONObject.class));

        return JSONObject.toJSONString(coin);
    }

    /**
     * 拼装转账数据
     */
    public static String packageData(String params) {
        Map<String, String> mapTxParams = JSONObject.parseObject(params, Map.class);
        Map<String, Object> txParams = new HashMap<String, Object>();
        txParams.putAll(mapTxParams);
        txParams.put("coin", JSONObject.parseObject(mapTxParams.get("coin"), MerchantCoinResponse.class));
        CreateTxRequest createTxRequest = TxUtil.packageData(txParams);
        return JSONObject.toJSONString(createTxRequest);
    }


    /**
     * 私钥签名
     *
     * @param params
     * @return
     */
    public static String getSignedTransaction(String params,byte chainId) {
        return TxUtil.getSignedTransaction20(params,chainId);
    }


    public String getCreateTxRequest(Map<String, String> coinMap) {
        Map<String, String> packageDataMap = new HashMap<>();
        packageDataMap.put("merchantId", coinMap.get("merchantId"));
        packageDataMap.put("privateKeyHex", private_key_hex);
        packageDataMap.put("amount", coinMap.get("amount"));
        packageDataMap.put("addressFrom", coinMap.get("addressFrom"));
        packageDataMap.put("addressTo", coinMap.get("addressTo"));
        packageDataMap.put("coinId", coinMap.get("coinId"));
        packageDataMap.put("coin", JSONObject.toJSONString(coinMap));
        packageDataMap.put("channel", coinMap.get("channel"));
        packageDataMap.put("gasPrice", coinMap.get("gasPrice"));
        packageDataMap.put("gasLimit", coinMap.get("gasLimit"));
        String createTxRequest = packageData(JSONObject.toJSONString(packageDataMap));
//        logger.info("createTxRequest:" + JSONObject.toJSONString(createTxRequest));
        return createTxRequest;
    }

    public String getSignTx(Map<String, String> coinMap, String txCreateUrl,byte chainId) {
        String nonceAndSingTx = this.getNonceAndSignTx(coinMap, txCreateUrl,chainId);
        return nonceAndSingTx.split("&")[1];
    }


    public String getNonceAndSignTx(Map<String, String> coinMap, String txCreateUrl,byte chainId) {
        String createTxRequest = this.getCreateTxRequest(coinMap);
        Map<String, String> signTxEncrypt = this.getEncrypt(createTxRequest);
        //构造创建signTx的请求
        Map<String, String> signPostData = new HashMap<>();
        signPostData.put("url", txCreateUrl);
        signPostData.put("params", signTxEncrypt.get("body"));
        signPostData.put("token", signTxEncrypt.get("token"));
        //发送请求创建转账对象
        String resultSingTx = PayUtil.doPost(signPostData);
//        logger.info("resultSingTx:" + resultSingTx);
        //解密回传回来的参数
        String resultSingTxDecrypt = this.getDecrypt(resultSingTx);
//        logger.info("resultSingTxDecrypt:" + resultSingTxDecrypt);
        Map<String, String> signTransactionMap = new HashMap<>();
        signTransactionMap.put("txMap", resultSingTxDecrypt);
        signTransactionMap.put("createTxRequest", createTxRequest);
        String signedTx = getSignedTransaction(JSONObject.toJSONString(signTransactionMap),chainId);
        return this.getNonce(resultSingTxDecrypt) + "&" + signedTx;
    }

    private String getNonce(String json) {
        Map<String, String> result = JSONObject.parseObject(json, Map.class);
        return result.get("txParams").split("&")[0];
    }

    /**
     * 退款方法
     *
     * @param orderNo     订单号
     * @param merchantId  商户号
     * @param channel     渠道号
     * @param coinId      币id
     * @param coinListUrl 请求币种url
     * @param txCreateUrl 创建提现请求url
     * @param withdrawUrl 提现请i去url
     * @return
     * @throws ApiException
     */
//    public String refundOrder(String orderNo, String merchantId, String channel, String coinId, String coinListUrl, String txCreateUrl, String withdrawUrl) throws ApiException {
//        String[] orders = orderNo.split(",");
//        Map<String, Object> orderMap = new HashMap<>();
//        orderMap.put("orderNo", orders);
//        orderMap.put("merchantId", merchantId);
//        orderMap.put("channel", channel);
//        //加密参数
//        Map<String, String> encryptResult = getEncrypt(JSONObject.toJSONString(orderMap));
//        //发送请求：请求订单信息
//        Map<String, String> doPostMap = new HashMap<>();
//        doPostMap.put("url", getOrderUrl);
//        doPostMap.put("params", encryptResult.get("body"));
//        doPostMap.put("token", encryptResult.get("token"));
//        String doPostResult = PayUtil.doPost(JSONObject.toJSONString(doPostMap));
//        //解密数据
//        String decryptResult = getDecrypt(doPostResult);
//        //将解密的订单数组转为对象
//        JSONArray jsonArray = JSONArray.parseArray(decryptResult);
//        //遍历数组查找是否有不满足条件的订单，若有则抛出异常
//        JSONObject jsonObject = jsonArray.getJSONObject(0);
//        if (Integer.parseInt(jsonObject.get("billType").toString()) != Config.PAY || Integer.parseInt(jsonObject.get("status").toString()) != Config.TRANSACTION_SUCCESS) {
//            throw new ApiException(StatusUtil.ORDER_INFO_ERROR, "该笔订单无法完成退款，请核实状态");
//        }
//        //若满足条件，则执行提现请求
//        //获取指定的币对象
//        Map<String, String> coinQueryParams = new HashMap<String, String>();
//        coinQueryParams.put("merchantId", merchantId);
//        coinQueryParams.put("channel", channel);
//        coinQueryParams.put("coinListUrl", coinListUrl);
//        coinQueryParams.put("coinId", coinId);
//        Map<String, String> coinMap = getCoin(coinQueryParams);
//
//        //注：如果不添加gasPrice或者gasLimit则采用后台系统的默认值
//        coinMap.put("gasPrice", "20000");
//        coinMap.put("gasLimit", "10000");
//        coinMap.put("coinId", coinId);
//        coinMap.put("merchantId", merchantId);
//        coinMap.put("amount", jsonObject.get("amount").toString());
//        coinMap.put("addressTo", jsonObject.get("fromAddr").toString());
//        coinMap.put("channel", channel);
//        //获取signTx
//        String signedTx = getSignTx(coinMap, txCreateUrl);
//
//        //组装提现参数
//        Map<String, String> withdrawRequest = new HashMap<String, String>();
//        withdrawRequest.put("merchantId", merchantId);
//        withdrawRequest.put("amount", jsonObject.get("amount").toString());
//        withdrawRequest.put("refBizNo", String.valueOf(System.currentTimeMillis()));
//        withdrawRequest.put("coinId", coinMap.get("coinId"));
//        withdrawRequest.put("fromAddr", jsonObject.get("toAddr").toString());
//        withdrawRequest.put("toAddr", jsonObject.get("fromAddr").toString());
//        withdrawRequest.put("signedTx", signedTx);
//        withdrawRequest.put("tokenAddr", coinMap.get("tokenAddress"));
//        withdrawRequest.put("channel", channel);
//        //加密提现请求
//        Map<String, String> txEncryptFinalMap = getEncrypt(JSONObject.toJSONString(withdrawRequest));
//        Map<String, String> txFinalPostMap = new HashMap<>();
//        txFinalPostMap.put("url", withdrawUrl);
//        txFinalPostMap.put("params", txEncryptFinalMap.get("body"));
//        txFinalPostMap.put("token", txEncryptFinalMap.get("token"));
//        String resultTxFinal = doPost(JSONObject.toJSONString(txFinalPostMap));
//        logger.info("resultTxFinal:" + resultTxFinal);
//        //解密提现请求结果.   注：若没有成功，会抛出一个Exception
//        String finalResult = getDecrypt(resultTxFinal);
//        logger.info(finalResult);
//        return finalResult;
//    }

//    public static void main(String[] args) {
//        TransactionCommand transactionCommand = new TransactionCommand("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgUtjUwQCpo5c49BHhU+k+DU5XYA5Ww9+Jeql9J4IzvHoW1ChX2tDiBPwB+pJbrUE9EEw+jEuj9QQIKyQwBbYpyoNh0uJMsGJJRcuIJYEsznw4wzvU/q9U0OcYJfQ9Qu5xQ3dH2yk1CS4v2Ai1v+wngZ1t4hcvKW2Ccbyh4SGxVQHhOCC4JchFHgmsRsytjIzZHxOGMvzhRy2fjHcYMyGNpgqHBMHx4sTtIdrKZ52MQ+A/Vjj3iznXbLNRxz5PtFO/c2iX8l6FbYj6iMXmcUZlUv08g7H1+hLObVHbDBLML/DkwQwpxz26f9S6jgR7XBBD31+tc5Vlede7cYWDLmx9QIDAQAB","MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCM0wuMJ5Od7z+nvds/C2vpyS6x7h6lUsrmGH9ANZoyIYwIzN4QWQuL940DPNF9t0SbDtffqbshwEjp3bWynQpC/EPLz4Od0zzvbf58pvfXaIh8nI8r3ldMTAmmtT64oZK2xrMwTJUWF5En1MFpCSr68yU4/X+2Rp1NpE2vimp34H2msxCoYi91FdBh5XcxjQPM4j2gGdY3kn2hcTSx5beKITb2bBW+nMKObT7JyTKFMFVUYQ0Yt/xghCK8Cz3dIAr20rLBFwJ4/suJlUHF6XadVxBFCxvdHlOEP1+02tEGhPb/TjMOSZO8zKXH6PaMyN8yrqafcMkEfPkZKUEWVjLXAgMBAAECggEALu7/gDyIdDCSlIMwGPjjhE0qpN5plEvdl4Y+ktXKiD6p/xzYPbaPpJxigNu6Lh3wXYU66S4WUg9VvavIrLit/Nk6dyi4AteuDVYpZL35B++s3WsIRGSbz6/f35G0T7B5xzS977D2VyCPnijINQt7KZvzoeTKtaMfNgs2YPTOuxi1TtnroDkqjhHplj4P/SHjalBI2ipXWyGQFgDKaexQafbESd+aNBR3d8PgOlouPBdlVJ5PZg9+LuWlLj+l7MqQZfreWN8YpVPYi+OreT+95j/C7QEDv/6kYQEqME03FZfAUCB2WI35aABuzzwcwzGb47+xnBH/+6M17hXULv+DsQKBgQDSTKw3AVwR+QqR3GTcocQGtWmxqKFFWcp+mri0HV8o2yBowhJVLsuVF+nyAyZSR1bZyJI+e/EOaoLFyEHiwtLAAr0DnNT5ZMzmqArUxqBMdRpMUbSdCFyTfD4adlHfFhVTH6OD2YpcUTFgtYEBYJjT8tpdvxM8gvYKzm2VcI0BrQKBgQCrbVnrd0nfy+z2XjgfCWAbWgwMOyGg5/2jeHJQloJzgxuMfdzzdMIcOyTzHHMAnExHw74R6Es0ZZY8iNaxGlfTy8xPS0s64t5+AFD6qhAwfIMTLCLkjbPMXYv7FT0e0cPvcvwc9/vh6Faf9PQKpxu7NKEYzTH4pNa6tNSJz/u/EwKBgQCtPlac1bToZXYMpTg9/dGECUzx+04n2ImVdJZ2Yr/y41m2kzIRFITH5TRnl6qpG3Xz4WSArw/tZVcn4gpM6Vw+q8LUSA23ChA2b/bERIDnm4y3lJOqzameIjrzB8f4dLPKj4Tf6IUv+f8ogT2uScp8yFKV6gPJ1MUmL9pcwqSW3QKBgBUdOt4Nq0OtUlpl8HOiK/wycxlujw3KhSsFQs86OocPtuzu86/oyZ/AiSSIdIG2vo54RYjfG6Qwfvilwgu+OuMTTu7VT0bJeq36S/8Twzq1m09ZOFL+QQ8C1qLuUsDhBUkQ2IyY3qVMgJ7jBMvrGTdNnCn53BXL98a3TxZKT6+RAoGAFrWw5xS6Huo38hV5ryoV+wOrAh11wA9ff5cuvuzAELWafmMoCynUwiZYcTlxeowe8cwByhoLQYv6bDezoa7Jls4yDDh06Ajbo3EhkI114BOVi6qgbZh3bcwRjCJjoUhI9HTycmzDxGS3KKM7/R5ObX6jKLClZaMlUiEsd0TyGoQ=","7820F3DF38FB27D46E1128FA45F4080F572695D95C7363A2D3E245E0F844317D");
////        String order = transactionCommand.refundOrder("OR1533864927620", "10000000000003", "73088886094000", "34190899187000", "http://47.100.18.6:9900/client/merchant/withdraw/queryWithdrawCoin", "http://47.100.18.6:9900/client/tx/txCreate", "http://47.100.18.6:9900/dcpayCore/payBills/withdraw");
////        logger.info("order-->" + order);
//
//        //获取币信息
//        Map<String, String> coinQueryParams = new HashMap<String, String>();
//        coinQueryParams.put("merchantId", merchantId);
//        coinQueryParams.put("channel", channel == null ? channel_default : channel);
//        coinQueryParams.put("coinListUrl", merchantCoinUrl);
//        coinQueryParams.put("coinId", coinId);
//        Map<String, String> coinMap = transactionCommand.getCoin(coinQueryParams);
//        if(coinMap == null){
//            logger.info(StatusUtil.POST_FAILED, "coin not exists!");
//        }
//        //添加参数
//        coinMap.put("coinId", coinId);
//        coinMap.put("merchantId", merchantId);
//        coinMap.put("amount", amount);
//        coinMap.put("addressTo", addressTo);
//        coinMap.put("channel", channel == null ? channel_default : channel);
//
//        //1. 构建txRequest
//        String nonceAndSignedTx = transactionCommand.getNonceAndSignTx(coinMap, createTxUrl);
//        String[] ns = nonceAndSignedTx.split("&");
//        //2. 构建提现请求
//        Map<String, String> withdrawRequest = new HashMap<String, String>();
//        withdrawRequest.put("merchantId", merchantId);
//        withdrawRequest.put("amount", amount);
//        withdrawRequest.put("refBizNo", String.valueOf(System.currentTimeMillis()));
//        withdrawRequest.put("coinId", coinMap.get("coinId"));
//        withdrawRequest.put("fromAddr", coinMap.get("addressFrom"));
//        withdrawRequest.put("toAddr", addressTo);
//        withdrawRequest.put("nonce", ns[0]);
//        withdrawRequest.put("signedTx", ns[1]);
//        withdrawRequest.put("tokenAddr", coinMap.get("tokenAddress"));
//        withdrawRequest.put("channel", channel);
//
//        //加密提现请求
//        Map<String,String> txEncryptFinalMap = transactionCommand.getEncrypt(JSONObject.toJSONString(withdrawRequest));
//        Map<String,String> txFinalPostMap = new HashMap<String ,String>();
//        txFinalPostMap.put("url", withdrawUrl);
//        txFinalPostMap.put("params",txEncryptFinalMap.get("body"));
//        txFinalPostMap.put("token",txEncryptFinalMap.get("token"));
//        String resultTxFinal = PayUtil.doPost(JSONObject.toJSONString(txFinalPostMap));
//        //解密提现请求结果.   注：若没有成功，会抛出一个Exception
//        String finalResult = transactionCommand.getDecrypt(resultTxFinal);
//        logger.info(finalResult);
//    }

}
