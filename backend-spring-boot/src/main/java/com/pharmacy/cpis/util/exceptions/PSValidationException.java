package com.pharmacy.cpis.util.exceptions;

public class PSValidationException extends PharmacySystemException{

    public PSValidationException() {
    }

    public PSValidationException(String message) {
        super(message);
    }

    public PSValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PSValidationException(Throwable cause) {
        super(cause);
    }

    public PSValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
