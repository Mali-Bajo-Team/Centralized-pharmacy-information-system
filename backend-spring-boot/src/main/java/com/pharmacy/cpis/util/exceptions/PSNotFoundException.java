package com.pharmacy.cpis.util.exceptions;

/*
 * Denotes that the user requested a resource which does not exist. This 
 * will result in a HTTP 404 Not Found response.
 */
public class PSNotFoundException extends PharmacySystemException {

	private static final long serialVersionUID = -2066591675232193474L;

	public PSNotFoundException() {
		super();
	}

	public PSNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PSNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PSNotFoundException(String message) {
		super(message);
	}

	public PSNotFoundException(Throwable cause) {
		super(cause);
	}

}
