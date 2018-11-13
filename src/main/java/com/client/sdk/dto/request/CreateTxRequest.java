package com.client.sdk.dto.request;


import com.client.sdk.dto.StandardEnum;

public class CreateTxRequest {

    private String coinId;
    private String addressFrom;//	转出账户地址
    private String addressTo;//	转入账户地址
    private String amount;//	转账数量（单位wei）
    private StandardEnum standard;//链类型
    private String gasPrice;//	ETH专有字段 gas单价（单位wei）
    private String gasLimit;//	ETH专有字段 gas个数上限
    private String memo;//	备注
    private String decimals;
    private String tokenAddress;
    private String merchantId;
    private String privateKeyHex;
    private String withdraw;// 是否提现业务
    private String channel;

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public StandardEnum getStandard() {
        return standard;
    }

    public void setStandard(StandardEnum standard) {
        this.standard = standard;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(String gasLimit) {
        this.gasLimit = gasLimit;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDecimals() {
        return decimals;
    }

    public String getPrivateKeyHex() {
        return privateKeyHex;
    }

    public void setPrivateKeyHex(String privateKeyHex) {
        this.privateKeyHex = privateKeyHex;
    }

    public void setDecimals(String decimals) {
        this.decimals = decimals;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
