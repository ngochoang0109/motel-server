package com.server.kltn.motel.api.user.payload;

import java.util.LinkedList;
import java.util.List;

public class PaymentPayload {
	private long id;
	List<OrderDetailPayload> orderDetailPayloads= new LinkedList<>();
	private long totalAmount;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<OrderDetailPayload> getOrderDetailPayloads() {
		return orderDetailPayloads;
	}
	public void setOrderDetailPayloads(List<OrderDetailPayload> orderDetailPayloads) {
		this.orderDetailPayloads = orderDetailPayloads;
	}
	public long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
}
