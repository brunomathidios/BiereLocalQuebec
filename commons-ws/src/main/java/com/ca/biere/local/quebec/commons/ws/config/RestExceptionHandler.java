package com.ca.biere.local.quebec.commons.ws.config;

import java.util.Arrays;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ca.biere.local.quebec.commons.ws.enums.EnumConstraintViolation;
import com.ca.biere.local.quebec.commons.ws.exception.ResourceNotFoundException;
import com.ca.biere.local.quebec.commons.ws.exception.SaveEntiteException;
import com.ca.biere.local.quebec.commons.ws.exception.ValidationException;
import com.ca.biere.local.quebec.commons.ws.pojo.Response;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Environment environment;
	
	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	public ResponseEntity<Response<Object>> handDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		
		if(ex.getCause() instanceof ConstraintViolationException) {
			
			ConstraintViolationException constraintException = (ConstraintViolationException) ex.getCause();
			
			EnumConstraintViolation constraintViolation = EnumConstraintViolation
					.getEnumByNomConstraint(constraintException.getConstraintName());
			
			if(Optional.ofNullable(constraintViolation).isPresent()) {
				
				return Response.status(HttpStatus.BAD_REQUEST.value(), Object.class)
						.message(constraintViolation.getMessage())
						.build();
			} else {
				
				/** esse trecho de código do ELSE é necessário, pois,
				 * o vendor H2 armazena o nome de constraint violation de outra maneira **/
				
				for(EnumConstraintViolation enumConstraint : EnumConstraintViolation.values()) {
					
					if(constraintException.getConstraintName().toUpperCase()
							.contains(enumConstraint.getNomConstraint().toUpperCase())) {
						
						return Response.status(HttpStatus.BAD_REQUEST.value(), Object.class)
								.message(enumConstraint.getMessage())
								.build();
					}
				}
			}
		}
		
		return Response.status(HttpStatus.BAD_REQUEST.value(), Object.class)
				.message(ex.getMessage())
				.build();
	}
	
	@ExceptionHandler(value = { ValidationException.class })
	public ResponseEntity<Response<Object>> handValidationException(ValidationException ex, WebRequest request) {
		
		log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
		return Response.status(HttpStatus.BAD_REQUEST.value(), Object.class)
				.messages(ex.getErrors())
				.build();
	}
	
	@ExceptionHandler(value = { SaveEntiteException.class })
	public ResponseEntity<Response<Object>> handSaveEntiteException(SaveEntiteException ex, WebRequest request) {
		
		log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
		
		return Response.status(HttpStatus.BAD_REQUEST.value(), Object.class)
				.messages(Arrays.asList(ex.getError()))
				.build();
	}

	@ExceptionHandler(value = { ResourceNotFoundException.class, EmptyResultDataAccessException.class })
	public ResponseEntity<Response<Object>> handleResourceNotFoundException(Exception ex, WebRequest request) {

		String errorMessage = this.environment.getRequiredProperty("message.error.notfound");

		if ( ex instanceof ResourceNotFoundException ) {
			errorMessage = ex.getMessage();
		}

		log.error(HttpStatus.NOT_FOUND.getReasonPhrase(), ex);
		return Response.status(HttpStatus.NOT_FOUND.value(), Object.class)
				.message(errorMessage)
				.build();
	}

	@ExceptionHandler(value = { MaxUploadSizeExceededException.class })
	public ResponseEntity<Response<Object>> handleFileSizeLimitExceededException(Exception ex, WebRequest request) {

		log.error(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED.getReasonPhrase(), ex);
		return Response.status(HttpStatus.BAD_REQUEST.value(), Object.class)
				.message(this.environment.getRequiredProperty("message.error.limit.exceed.upload"))
				.build();
	}

//	@ExceptionHandler(value = { InvalidUserException.class })
//	public ResponseEntity<Response<Object>> handleInvalidUserException(InvalidUserException ex, WebRequest request) {
//
//		log.error(HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex);
//		return Response.status(HttpStatus.UNAUTHORIZED.value(), Object.class)
//				.message(ex.getMessage())
//				.build();
//	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	public ResponseEntity<Response<Object>> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {

		log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
		return Response.status(HttpStatus.BAD_REQUEST.value(), Object.class)
				.message(ex.getMessage())
				.build();
	}

//	@ExceptionHandler(value = { UsernameNotFoundException.class, BadCredentialsException.class })
//	public ResponseEntity<Response<Object>> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
//
//		log.error(HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex);
//		return Response.status(HttpStatus.UNAUTHORIZED.value(), Object.class)
//				.message(environment.getRequiredProperty("message.error.forbiden.usernameandpassword"))
//				.build();
//	}

//	@ExceptionHandler(value = { CredentialsExpiredException.class })
//	public ResponseEntity<Response<Object>> handleCredentialsExpiredException(CredentialsExpiredException ex, WebRequest request) {
//
//		log.error(HttpStatus.FORBIDDEN.getReasonPhrase(), ex);
//		return Response.status(HttpStatus.FORBIDDEN.value(), Object.class)
//				.message(environment.getRequiredProperty("message.error.forbiden.tokenexpired"))
//				.build();
//	}

//	@ExceptionHandler(value = { ExpiredJwtException.class })
//	public ResponseEntity<Response<Object>> handleException(ExpiredJwtException ex, WebRequest request) {
//
//		log.error(HttpStatus.FORBIDDEN.getReasonPhrase(), ex);
//		return Response.status(HttpStatus.FORBIDDEN.value(), Object.class)
//				.message(environment.getRequiredProperty("message.error.forbiden.tokenexpired"))
//				.build();
//
//	}

//	@ExceptionHandler(value = { JwtException.class })
//	public ResponseEntity<Response<Object>> handleException(JwtException ex, WebRequest request) {
//
//		log.error(HttpStatus.FORBIDDEN.getReasonPhrase(), ex);
//		return Response.status(HttpStatus.FORBIDDEN.value(), Object.class)
//				.message(environment.getRequiredProperty("message.error.forbiden.tokenexpired"))
//				.build();
//
//	}

//	@ExceptionHandler(value = { AuthenticationCredentialsNotFoundException.class, AuthenticationException.class })
//	public ResponseEntity<Response<Object>> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex, WebRequest request) {
//
//		log.error(HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex);
//		return Response.status(HttpStatus.UNAUTHORIZED.value(), Object.class)
//				.message(environment.getRequiredProperty("message.error.forbiden.invalidtoken"))
//				.build();
//	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Response<Object>> handleGeneralException(Exception ex, WebRequest request) {

		final ResponseEntity<Response<Object>> response =
				Response.status(HttpStatus.INTERNAL_SERVER_ERROR.value(), Object.class)
				.message(this.environment.getRequiredProperty("message.error.internalerror"))
				.exception(ex)
				.build();
		
		final String errorCode = response.getBody().getErrorCode();
		log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase().concat(" - CODE ").concat( errorCode ), ex);
		
		return response;
	}
}
