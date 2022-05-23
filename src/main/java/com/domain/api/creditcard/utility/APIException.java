package com.domain.api.creditcard.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<Message> errorMessages = new ArrayList<>();
	
	HttpStatus status;
	
	public APIException() {
		super();
		
	}

	public APIException(List<Message> errorMessages, HttpStatus status) {
		super();
		this.errorMessages = errorMessages;
		this.status = status;
	}
	
	public APIException(Message errorMessage, HttpStatus status) {
		super();
		this.errorMessages.add(errorMessage);
		this.status = status;
	}

	public List<Message> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<Message> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
}
