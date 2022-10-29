package com.server.kltn.motel.api.admin.payload;

public class RejectDatasource {
	private long id;
	private String reason;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
