package com.pharmacy.cpis.util.exceptions;

public class PharmacySystemException extends RuntimeException {

	private static final long serialVersionUID = 604445251879335961L;

	public PharmacySystemException() {
    }

    public PharmacySystemException(String message) {
        super(message);
    }

    public PharmacySystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public PharmacySystemException(Throwable cause) {
        super(cause);
    }

    public PharmacySystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
