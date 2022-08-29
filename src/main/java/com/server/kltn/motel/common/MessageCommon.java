package com.server.kltn.motel.common;

public class MessageCommon{
	private boolean status;
	private String message;
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageCommon(boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	
}
