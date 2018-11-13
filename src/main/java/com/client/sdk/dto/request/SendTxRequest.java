package com.client.sdk.dto.request;

public class SendTxRequest {

    private String coinId;
    private String addressFrom;//	转出账户地址
    private String addressTo;//	转入账户地址
    private String amount;//	转账数量（单位wei）
    private String standard;//链类型
    private String gasPrice;//	ETH专有字段 gas单价（单位wei）
    private String gasLimit;//	ETH专有字段 gas个数上限
    private String memo;//	备注
    private String decimals;

    private String signedTx;
    private String tokenAddress;


}
