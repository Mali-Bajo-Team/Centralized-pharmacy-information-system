package com.pharmacy.cpis.util.exceptions;

/*
 * Denotes that the user tried to create a resource that already exists (for example, 
 * someone tried to register an account with an email that is already in use). This 
 * will result in a HTTP 409 Conflict response.
 */
public class PSAlreadyExistsException extends PharmacySystemException {

	private static final long serialVersionUID = -4782673556690105083L;

	public PSAlreadyExistsException() {
		super();
	}

	public PSAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PSAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public PSAlreadyExistsException(String message) {
		super(message);
	}

	public PSAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
