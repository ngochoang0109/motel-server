package com.server.kltn.motel.api.admin.payload;

import java.util.List;

public class DiscountDatasource {
	private String code;
	private int percent;
	private long price;
	private String startedDate;
	private String endDate;
	private String description;
	
	public DiscountDatasource(String code, int percent, long price, String startedDate, String endDate,
			String description, List<ExpenseDatasource> expenseDatasources) {
		super();
		this.code = code;
		this.percent = percent;
		this.price = price;
		this.startedDate = startedDate;
		this.endDate = endDate;
		this.description = description;
		this.expenseDatasources = expenseDatasources;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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