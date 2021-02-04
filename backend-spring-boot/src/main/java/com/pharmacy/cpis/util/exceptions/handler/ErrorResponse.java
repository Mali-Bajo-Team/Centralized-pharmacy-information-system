package com.pharmacy.cpis.util.exceptions.handler;

import java.util.List;

/*
 * The response body that will be returned if an exception is handled.
 */
public class ErrorResponse {
	private String message;
	private List<String> details;

	public ErrorResponse(String message, List<String> details) {
		super();
		this.message = message;
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}
}
