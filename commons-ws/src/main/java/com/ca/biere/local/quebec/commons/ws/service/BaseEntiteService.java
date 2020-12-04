package com.ca.biere.local.quebec.commons.ws.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import com.ca.biere.local.quebec.commons.ws.exception.ValidationException;

public abstract class BaseEntiteService<T> {

	public abstract T insert(T t);
	
	public abstract T update(T t);
	
	protected Validator validator;
	
	protected void setValidator(Validator validator) {
		this.validator = validator;
	}
	
	protected void valideEntite(T t) {
		Set<ConstraintViolation<T>> violations = this.validator.validate(t, Default.class);
		
		if (violations.size() > 0) {
			throw new ValidationException(violations
					.stream()
					.map( ConstraintViolation::getMessage )
					.collect( Collectors.toList() ) );
		}
	}
}
