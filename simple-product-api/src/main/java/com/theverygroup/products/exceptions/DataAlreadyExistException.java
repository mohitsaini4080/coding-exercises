package com.theverygroup.products.exceptions;

/**
 * @implNote Class to manage custom exception
 */
@SuppressWarnings("serial")
public class DataAlreadyExistException extends RuntimeException{
	 public DataAlreadyExistException(String msg) {
	        super(msg);
}
}
