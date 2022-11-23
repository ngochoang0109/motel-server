package com.server.kltn.motel.api.user.payload;

public class PayOrder {
	
	private long paymentId;
	private String bankCode;
	private String ipUser;
	
	public String getIpUser() {
		return ipUser;
	}
	public void setIpUser(String ipUser) {
		this.ipUser = ipUser;
	}
	public long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
}
