package com.client.sdk.dto.response;

public class PrePayResponse {

//{"code":"200","message":"成功","data":
// {"merchantId":"10000000000003","payBillNo":"91550692916800",
// "refBizNo":"234324234","coinId":"10880412808400",
// "amount":"0.1000","orginAmount":"0",
// "toAddr":"0x91f8654587917f3a0c7cfc5fa05bd86dc0162ddb",
// "tokenAddr":"0x4dcf374a9affa8d43c503a79dfd9edf95677c0f1",
// "txData":"0xa9059cbb00000000000000000000000091f8654587917f3a0c7cfc5fa05bd86dc0162ddb000000000000000000000000000000000000000000000000016345785d8a0000",
// "attach":"sdfsdf"}}

    private String merchantId;
    private String payBillNo;
    private String refBizNo;
    private String coinId;
    private String amount;
    private String orginAmount;
    private String toAddr;
    private String tokenAddr;
    private String txData;
    private String attach;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPayBillNo() {
        return payBillNo;
    }

    public void setPayBillNo(String payBillNo) {
        this.payBillNo = payBillNo;
    }

    public String getRefBizNo() {
        return refBizNo;
    }

    public void setRefBizNo(String refBizNo) {
        this.refBizNo = refBizNo;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrginAmount() {
        return orginAmount;
    }

    public void setOrginAmount(String orginAmount) {
        this.orginAmount = orginAmount;
    }

    public String getToAddr() {
        return toAddr;
    }

    public void setToAddr(String toAddr) {
        this.toAddr = toAddr;
    }

    public String getTokenAddr() {
        return tokenAddr;
    }

    public void setTokenAddr(String tokenAddr) {
        this.tokenAddr = tokenAddr;
    }

    public String getTxData() {
        return txData;
    }

    public void setTxData(String txData) {
        this.txData = txData;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}
