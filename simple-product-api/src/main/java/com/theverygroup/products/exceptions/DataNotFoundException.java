package com.theverygroup.products.exceptions;


/**
 * @implNote Class to manage custom exception
 */
@SuppressWarnings("serial")
public class DataNotFoundException extends RuntimeException {
	public DataNotFoundException(String msg) {
        super(msg);
    }
}
