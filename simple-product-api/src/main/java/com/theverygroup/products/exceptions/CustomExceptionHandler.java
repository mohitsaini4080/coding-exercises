package com.theverygroup.products.exceptions;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
     * @return responseBuilder
     * @implNote Function to define Custom Exception when data is already exists.
     */
	@ExceptionHandler(DataAlreadyExistException.class)
	public ResponseEntity<Object> handleResourceExistsException(DataAlreadyExistException ex) {
		return responseBuilder(new ApiMessageHandler(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now()));
	}

	/**
     * @return responseBuilder
     * @implNote Function to define Custom Exception when data is not present.
     */
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(DataNotFoundException ex) {
		return responseBuilder(new ApiMessageHandler(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now()));
	}

	/**
     * @return responseBuilder
     * @implNote Function to define Custom message.
     */
	private ResponseEntity<Object> responseBuilder(ApiMessageHandler apiException) {
		return new ResponseEntity<>(apiException, apiException.getStatus());
	}

}
