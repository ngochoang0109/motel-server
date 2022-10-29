package com.server.kltn.motel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.server.kltn.motel.common.MessageCommon;

@ControllerAdvice
public class ExceptionController<T> {
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<MessageCommon> handlerBadRequestException(BadRequestException badRequestException){
		MessageCommon message= new MessageCommon(false,badRequestException.getMessage());
		return new ResponseEntity<MessageCommon>(message, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<MessageCommon> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, 
			WebRequest webRequest){
		MessageCommon message= new MessageCommon(false,resourceNotFoundException.getMessage());
		return new ResponseEntity<MessageCommon>(message, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<MessageCommon> handleExpiredJwtException(ExpiredJwtException expiredJwtException, 
			WebRequest webRequest){
		MessageCommon message= new MessageCommon(false,expiredJwtException.getMessage());
		return new ResponseEntity<MessageCommon>(message, HttpStatus.NOT_FOUND);
	}
}
