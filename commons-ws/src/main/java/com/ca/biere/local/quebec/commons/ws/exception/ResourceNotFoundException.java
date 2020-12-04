package com.ca.biere.local.quebec.commons.ws.exception;

import java.util.Arrays;
import java.util.List;

public class ResourceNotFoundException extends RuntimeException {

	private List<String> errors;

	public ResourceNotFoundException(String message) {
		super( message );
		this.errors = Arrays.asList( message );
	}

	public ResourceNotFoundException(List<String> errors) {
		super( String.format( "Erro de validação: %s", errors ) );
		this.errors = errors;
	}

	public List<String> getErrors() {
		return this.errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}