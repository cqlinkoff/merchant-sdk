package com.client.sdk.dto.request;

import javax.validation.constraints.NotNull;

public class WithdrawRequest {
	@NotNull
	private String refBizNo;
	@NotNull
	private String coinId;
	@NotNull
	private String amount;
	private String toAddr;

	private String tokenAddr;
	@NotNull
	private String fromAddr;
	@NotNull
	private String merchantId;
	@NotNull
	private String channel;

	private Long nonce;
	/**
	 * 包含to from value data
	 */
	@NotNull
	private String signedTx;

	private String attach;

	public Long getNonce() {
		return nonce;
	}

	public void setNonce(Long nonce) {
		this.nonce = nonce;
	}

	public String getTokenAddr() {
		return tokenAddr;
	}

	public void setTokenAddr(String tokenAddr) {
		this.tokenAddr = tokenAddr;
	}

	public String getFromAddr() {
		return fromAddr;
	}

	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}

	public String getToAddr() {
		return toAddr;
	}

	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}

	public String getSignedTx() {
		return signedTx;
	}

	public void setSignedTx(String signedTx) {
		this.signedTx = signedTx;
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

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
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
