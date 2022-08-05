package com.server.kltn.motel.exception;


public class ExpiredJwtException extends RuntimeException{
	
	private String message;

	public ExpiredJwtException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
