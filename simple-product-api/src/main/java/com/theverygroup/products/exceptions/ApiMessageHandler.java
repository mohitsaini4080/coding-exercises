package com.theverygroup.products.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

/**
 * @implNote Class to manage custom messages
 */

public class ApiMessageHandler {
	
	private String message;
	private HttpStatus status;
    private LocalDateTime timestamp;
    
	
    public ApiMessageHandler(String message, HttpStatus status, LocalDateTime timestamp) {
		super();
		this.message = message;
		this.status = status;
		this.timestamp = timestamp;
		
	}

    public String getMessage() {
		return message;
	}
    
	public HttpStatus getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	
   
}
