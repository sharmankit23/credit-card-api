package com.domain.api.creditcard.utility;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionControllerAdvice{
	
	@ExceptionHandler({APIException.class})
	protected ResponseEntity<List<Message>> handleApiException(APIException ex, WebRequest request){
	
		
		return ResponseEntity.status(ex.getStatus()).body(ex.getErrorMessages());
		
		
	}

}
