package com.pharmacy.cpis.util.exceptions;

public class PSForbiddenException extends PharmacySystemException {

	private static final long serialVersionUID = -4551528824275873904L;

	public PSForbiddenException() {
		super();
	}

	public PSForbiddenException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PSForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	public PSForbiddenException(String message) {
		super(message);
	}

	public PSForbiddenException(Throwable cause) {
		super(cause);
	}

}
