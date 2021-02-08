package com.pharmacy.cpis.util.exceptions;

/*
 * Denotes that the request cannot be completed because it is in conflict with the current state 
 * of the resouce (for example, creating a resource that already exists). This will result in a 
 * HTTP 409 Conflict response.
 */
public class PSConflictException extends PharmacySystemException {

	private static final long serialVersionUID = -4782673556690105083L;

	public PSConflictException() {
		super();
	}

	public PSConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PSConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public PSConflictException(String message) {
		super(message);
	}

	public PSConflictException(Throwable cause) {
		super(cause);
	}

}
