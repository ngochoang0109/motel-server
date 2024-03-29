package com.server.kltn.motel.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerException extends RuntimeException{
	private String message;

	public ServerException(String message) {
		this.message = message;
	}
}
