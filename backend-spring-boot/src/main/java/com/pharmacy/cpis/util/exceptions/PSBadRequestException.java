package com.pharmacy.cpis.util.exceptions;

/*
 * Denotes that the request fails some sort of logical condition. This 
 * will result in a HTTP 400 Bad Request response.
 */
public class PSBadRequestException extends PharmacySystemException{

	private static final long serialVersionUID = -6013397092569480495L;

	public PSBadRequestException() {
    }

    public PSBadRequestException(String message) {
        super(message);
    }

    public PSBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public PSBadRequestException(Throwable cause) {
        super(cause);
    }

    public PSBadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
