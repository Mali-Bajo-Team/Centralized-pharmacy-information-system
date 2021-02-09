package com.pharmacy.cpis.scheduleservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class VacationReviewDTO {

	@NotEmpty(message = "Response is requried.")
	@Size(max = 500, message = "Maximum length of response is 500 characters.")
	private String response;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
