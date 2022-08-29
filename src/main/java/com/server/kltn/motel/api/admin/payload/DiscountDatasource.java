package com.server.kltn.motel.api.admin.payload;

import java.util.List;

public class DiscountDatasource {
	private String code;
	private int percent;
	private long price;
	private String startedDate;
	private String endDate;
	private List<ExpenseDatasource> expenseDatasources;
	
	public List<ExpenseDatasource> getExpenseDatasources() {
		return expenseDatasources;
	}
	public void setExpenseDatasources(List<ExpenseDatasource> expenseDatasources) {
		this.expenseDatasources = expenseDatasources;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
