package com.server.kltn.motel.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resource;
	private String field;
	private long value;
	private String data;
	
	public ResourceNotFoundException(String resource, String field, long value) {
		super(String.format("%s không tồn tại %s: %s", resource, field, value));
		this.resource = resource;
		this.field = field;
		this.value = value;
	}
	
	
	 
	public ResourceNotFoundException(String resource, String field, String data) {
		super(String.format("%s không tồn tại %s: %s", resource, field, data));
		this.resource = resource;
		this.field = field;
		this.data = data;
	}



	public String getResource() {
		return resource;
	}



	public void setResource(String resource) {
		this.resource = resource;
	}



	public String getField() {
		return field;
	}



	public void setField(String field) {
		this.field = field;
	}



	public long getValue() {
		return value;
	}



	public void setValue(long value) {
		this.value = value;
	}



	public String getData() {
		return data;
	}



	public void setData(String data) {
		this.data = data;
	}
}
