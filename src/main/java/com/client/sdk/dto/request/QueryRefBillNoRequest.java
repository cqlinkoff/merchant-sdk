package com.client.sdk.dto.request;

public class QueryRefBillNoRequest {

    private Long[] orderNo;
    private String merchantId;
    private String channel;
    public Long[] getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long[] orderNo) {
        this.orderNo = orderNo;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}