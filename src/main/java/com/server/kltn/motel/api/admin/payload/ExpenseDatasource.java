package com.server.kltn.motel.api.admin.payload;

public class ExpenseDatasource {
	private long id;
	private String type;
	private long cost;
	
	public long getId() {
		return id;
	}
	
	public ExpenseDatasource() {
		super();
	}

	public ExpenseDatasource(long id, String type, long cost) {
		super();
		this.id = id;
		this.type = type;
		this.cost = cost;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getCost() {
		return cost;
	}
	public void setCost(long cost) {
		this.cost = cost;
	}
}