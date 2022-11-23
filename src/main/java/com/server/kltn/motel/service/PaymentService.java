package com.server.kltn.motel.service;

import java.util.List;

import com.server.kltn.motel.api.user.payload.HistoryPayload;
import com.server.kltn.motel.api.user.payload.PayOrder;
import com.server.kltn.motel.api.user.payload.PaymentPayload;

public interface PaymentService {
	long createPayment(String username, long cartId);
	PaymentPayload getPaymentDetail(long paymentId);
	String payVnpay(PayOrder payOrder);
	void checkUpdatePayment(long paymentId, String payDate, String responseCode);
	List<HistoryPayload> getHistoryPayment(String username);
}