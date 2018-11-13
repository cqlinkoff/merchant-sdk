package com.client.utils;

/**
 * Created by Ethan.Yuan on 2018/4/19.
 */
public interface StatusUtil {
    /**
     * 状态成功
     */
    String SUCCESS = "200";

    /**
     * 签名验证失败
     */
     String SIGNATURE_FAILED = "201";
    /**
     * 解密数据失败
     */
     String DECRYPT_FAILED = "202";

    /**
     *消息头构建失败
     */
     String HEADER_ENCODE_FAILED = "202";

     String POST_FAILED = "203";

    /**
     * 请求地址为空
     */
    String URL_INVALID = "301";

    /**
     * 请求参数为空
     */
    String PARAMETER_INVALID = "302";

    /**
     * 请求token为空
     */
    String TOKEN_INVALID = "303";


    /**
     * 编码错误
     */
    String UNSUPPORTED_ENCODING = "304";


    /**
     * 预支付请求失败
     */
     String PRE_PAY_REQUEST_FAILED = "500";

    /**
     * 提现请求失败
     */
    String WITHDRAW_REQUEST_FAILED = "501";

    /**
     * 对账请求失败
     */
    String ACCOUNTCHECK_REQUEST_FAILED = "502";

    String ORDER_INFO_ERROR = "601";
}
