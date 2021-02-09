package com.pharmacy.cpis.userservice.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EmployDermatologistDTO {

	@NotNull(message = "Dermatologist id is required.")
	private Long dermatologistId;

	@NotNull(message = "Working times are required.")
	@Valid
	private AddWorkingTimeDTO workingTimes;

	public Long getDermatologistId() {
		return dermatologistId;
	}

	public void setDermatologistId(Long dermatologistId) {
		this.dermatologistId = dermatologistId;
	}

	public AddWorkingTimeDTO getWorkingTimes() {
		return workingTimes;
	}

	public void setWorkingTimes(AddWorkingTimeDTO workingTimes) {
		this.workingTimes = workingTimes;
	}

}
