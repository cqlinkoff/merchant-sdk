package com.client.utils.PayUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.client.exception.ApiException;
import com.client.sdk.base.BaseClass;
import com.client.sdk.dto.StandardEnum;
import com.client.sdk.dto.eth.BGETHTXHelper;
import com.client.sdk.dto.request.CreateTxRequest;
import com.client.sdk.dto.response.BaseResponse;
import com.client.sdk.dto.response.MerchantCoinResponse;
import com.client.utils.ResultUtil;
import com.client.utils.security.DCPEncryptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.tx.ChainId;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class TxUtil extends BasePayUtil{
    private static Logger logger = LoggerFactory.getLogger(TxUtil.class);
    private final static String[] requiredArray = {"tokenAddress", "standard", "coinId", "decimals", "description", "coinName","addressForm"};

    //解密
    public static ResultUtil getTxDecryptData(String body, String mbrPublic, String merchantPrivate)throws ApiException{

        Map<String,String> mapData = JSONObject.parseObject(body,Map.class);
        boolean b = DCPEncryptor.verifySignature(mapData.get("signature"),"RSA2",mapData.get("key"),mapData.get("iv"),mapData.get("cipher"),mbrPublic);
        if (b) {
            byte[] responseBodyByte = DCPEncryptor.decrypt((String)mapData.get("key"), (String)mapData.get("iv"), (String)mapData.get("cipher"), merchantPrivate);
            String bodyString = "";

            try {
                bodyString = new String(responseBodyByte, "UTF-8");
            } catch (Exception var13) {
                return BaseClass.failed("500");
            }

            BaseResponse baseResponse = (BaseResponse)JSONObject.toJavaObject(JSON.parseObject(bodyString), BaseResponse.class);
            if (baseResponse.getCode().equals("200")) {
                try {
                    return BaseClass.success(baseResponse.getData());
                } catch (ApiException var11) {
                    return BaseClass.failed(var11.getMessage());
                } catch (Exception var12) {
                    return BaseClass.failed(var12.getMessage());
                }
            } else {
                return BaseClass.failed("500","请求失败");
            }
        } else {
            return BaseClass.failed("201","验签失败");
        }
    }

    /**
     * 查找对应的币种
     */
    public static MerchantCoinResponse searchForCoin(String coinId,List<JSONObject> coinList){
        MerchantCoinResponse coin = null;
        //0.1 查找对应的币种
        for(JSONObject mCoin : coinList){
            if(mCoin.getString("coinId").equals(coinId)){
                coin = new MerchantCoinResponse();
                coin.setCoinId(coinId);
                coin.setDecimals(mCoin.getString("decimals"));
                coin.setTokenAddress(mCoin.getString("tokenAddress"));
                coin.setAddressFrom(mCoin.getString("addressFrom"));
                break;
            }
        }
        return coin;
    }

    /**
     * 拼装转账数据
     */
    public static CreateTxRequest packageData(Map<String, Object> params){
        Object gasPrice = params.get("gasPrice");
        Object gasLimit = params.get("gasLimit");
        if(null == gasPrice || null == gasLimit){
            return packageData(params, "200000000000", "2000000");
        }else{
            return packageData(params, gasPrice.toString(), gasLimit.toString());
        }

    }

    /**
     *
     * @param params
     * @param gasPrice 单位为wei
     * @param gasLimit
     * @return
     */
    public static CreateTxRequest packageData(Map<String, Object> params, String gasPrice, String gasLimit){
        String merchantId = params.get("merchantId").toString();
        String addressFrom = params.get("addressFrom").toString();
        String addressTo = params.get("addressTo").toString();
        String amount = params.get("amount").toString();
        String privateKeyHex = params.get("privateKeyHex").toString();
        String channel = params.get("channel").toString();
        MerchantCoinResponse coin = (MerchantCoinResponse)params.get("coin");
        CreateTxRequest createTxRequest = new CreateTxRequest();
        createTxRequest.setCoinId(coin.getCoinId());
        createTxRequest.setAddressFrom(addressFrom);//	转出账户地址
        createTxRequest.setAddressTo(addressTo);//	转入账户地址
        createTxRequest.setAmount(calculateTxAmount(amount, coin.getDecimals()));//	转账数量（单位wei）
        if(coin.getCoinId().equals("34190899187000")){
            createTxRequest.setStandard(StandardEnum.ETH);//链类型
        }else{
            createTxRequest.setStandard(StandardEnum.ERC20);
        }
        createTxRequest.setGasPrice(gasPrice);//	ETH专有字段 gas单价（单位wei）
        createTxRequest.setGasLimit(gasLimit);//	ETH专有字段 gas个数上限
        createTxRequest.setMemo("提现");//	备注
        createTxRequest.setDecimals(coin.getDecimals());
        createTxRequest.setTokenAddress(coin.getTokenAddress());
        createTxRequest.setMerchantId(merchantId);
        createTxRequest.setPrivateKeyHex(privateKeyHex);
        createTxRequest.setWithdraw("true");
        createTxRequest.setChannel(channel);
        return createTxRequest;
    }


    private static String calculateTxAmount(String amount, String decimals){
        BigDecimal bd = new BigDecimal(amount);
        bd = bd.multiply(new BigDecimal(10).pow(Integer.valueOf(decimals)));
        String str = bd.toString();
        int index = str.indexOf(".");
        if(index!=-1){
            str = str.substring(0,index);
        }
        return str;
    }
    public static String getSignedTransaction20(String params,byte chainId){
        Map<String,String> map = JSONObject.parseObject(params,Map.class);
        Map<String ,String> txMap = JSONObject.parseObject(map.get("txMap"),Map.class);

        CreateTxRequest txRequest = JSONObject.parseObject(map.get("createTxRequest"),CreateTxRequest.class);
        logger.info("txRequest**->{}"+JSON.toJSONString(txRequest));
        String signedTransaction20 = "";
        if (StringUtils.isNotEmpty((String)txMap.get("txParams"))) {
            String[] txParams = ((String)txMap.get("txParams")).split("&");
            BigInteger nonce = new BigInteger(txParams[0]);
            BigInteger gasPriceFromServer = new BigInteger(txParams[1]);
            BigInteger gasLimitFromServer = new BigInteger(txParams[2]);
            RawTransaction rawTransaction;
            BigInteger amountBigInteger = new BigInteger(txRequest.getAmount());
            logger.info("amount->{}",amountBigInteger);
            if (StandardEnum.ETH.equals(txRequest.getStandard())) {
                rawTransaction = BGETHTXHelper.createEthTransaction(nonce, gasPriceFromServer, gasLimitFromServer, txRequest.getAddressTo(), amountBigInteger);
            }
            else {
                rawTransaction = BGETHTXHelper.createErc20Transaction(nonce, gasPriceFromServer, gasLimitFromServer, txRequest.getAddressTo(), amountBigInteger, txRequest.getTokenAddress());
            }
            String priHexFrom = txRequest.getPrivateKeyHex();
            Credentials credentialsFrom = Credentials.create(priHexFrom);
            signedTransaction20 = BGETHTXHelper.signTransaction(rawTransaction, chainId, credentialsFrom);
        }
        return signedTransaction20;
    }

}
