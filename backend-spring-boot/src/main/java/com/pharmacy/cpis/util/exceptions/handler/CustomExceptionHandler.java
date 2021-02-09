package com.pharmacy.cpis.util.exceptions.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pharmacy.cpis.util.exceptions.PSBadRequestException;
import com.pharmacy.cpis.util.exceptions.PSConflictException;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder details = new StringBuilder();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.append(error.getDefaultMessage());
			details.append(" ");
		}
		ErrorResponse error = new ErrorResponse("Request parameters are not valid.", details.toString());
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PSNotFoundException.class)
	public ResponseEntity<Object> notFound(PSNotFoundException ex) {
		ErrorResponse response = new ErrorResponse("The requested resource does not exist.", ex.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PSBadRequestException.class)
	public ResponseEntity<Object> badRequest(PSBadRequestException ex) {
		ErrorResponse response = new ErrorResponse("Bad request.", ex.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PSConflictException.class)
	public ResponseEntity<Object> alreadyExists(PSConflictException ex) {
		ErrorResponse response = new ErrorResponse("The request is in conflict with the current state of the resource.",
				ex.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(PSForbiddenException.class)
	public ResponseEntity<Object> alreadyExists(PSForbiddenException ex) {
		ErrorResponse response = new ErrorResponse("You do not have permission to access the requested resource.",
				ex.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.FORBIDDEN);
	}

}
