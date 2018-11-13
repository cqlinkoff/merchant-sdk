package com.client.sdk.dto.response;

/**
 * 支付通知
 */
public class PayNotifyResponse {

    private String notifyId;
    private String payBillNo;
    private String refBizNo;
    private String coinId;
    private String amount;
    private String goodsYype;
    private String goodsTag;
    private String goodsName;
    private String txHash;
    private String resultCode;
    private String attach;

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
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

    public String getGoodsYype() {
        return goodsYype;
    }

    public void setGoodsYype(String goodsYype) {
        this.goodsYype = goodsYype;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}
