package com.server.kltn.motel.service;

import com.server.kltn.motel.api.user.payload.PaymentPayload;

public interface PaymentService {
	long createPayment(String username, long cartId);
	PaymentPayload getPaymentDetail(long paymentId);
}
