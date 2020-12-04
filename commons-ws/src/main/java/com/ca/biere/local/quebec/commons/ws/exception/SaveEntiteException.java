package com.ca.biere.local.quebec.commons.ws.exception;

public class SaveEntiteException extends RuntimeException {

	private String error;
	
	public SaveEntiteException(String error) {
		super(error);
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
