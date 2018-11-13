package com.client.sdk.dto.request;



import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 预支付请求对象
 */
public class PrePayRequest implements Serializable {
   // {@"amount":@"1",@"attach":@"api_prepay",@"coinId":@"10880412808400",
    // @"goodsTag":@"成人用品",@"goodsType":@"情趣内衣",@"refBizNo":@"2000010006"};
    /**
     * 金额
     */
    @NotNull
    private String amount;
    /**
     * 透传字段
     */
    private String attach;
    /**
     * 商品二级分类
     */
    private String goodsTag;
    /**
     * 商品类型
     */
    private String goodsType;
    /**
     * 外部商户订单号
     */
    @NotNull
    private String refBizNo;
    /**
     * 币id
     */
    @NotNull
    private String coinId;

    private String merchantId;

    private String channel;

    private String toAddress;

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
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
