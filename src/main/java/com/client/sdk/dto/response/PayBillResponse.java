package com.client.sdk.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PayBillResponse implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long parentNo;// dcpay 订单号


    private String refBizNo;// 商户订单号

    private String coinName;// 支付币名称

    private BigDecimal amount;//金额


    private String merchantName;// 商户名称

    private String attach;//透传字段

    private String toAddr;//
    private String tokenAddr;//合约地址
    private String txHash;// hash

    private String fromAddr;
    private Integer billType;// 支付类型 0 提现 1 充值
    private Integer status; // 状态 0 初始  1 处理中 2 成功 3 失败
    private Long createTime;//支付时间
    private Long completeTime;//到账时间

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getParentNo() {
        return parentNo;
    }

    public void setParentNo(Long parentNo) {
        this.parentNo = parentNo;
    }

    public String getRefBizNo() {
        return refBizNo;
    }

    public void setRefBizNo(String refBizNo) {
        this.refBizNo = refBizNo;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
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

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getFromAddr() {
        return fromAddr;
    }

    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Long completeTime) {
        this.completeTime = completeTime;
    }
}