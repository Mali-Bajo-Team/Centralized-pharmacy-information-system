package com.pharmacy.cpis.userservice.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EmployPharmacistDTO {

	@NotNull(message = "Pharmacist details are required.")
	@Valid
	private UserRegisterDTO pharmacist;

	@NotNull(message = "Working times are required.")
	@Valid
	private AddWorkingTimeDTO workingTimes;

	public UserRegisterDTO getPharmacist() {
		return pharmacist;
	}

	public void setPharmacist(UserRegisterDTO pharmacist) {
		this.pharmacist = pharmacist;
	}

	public AddWorkingTimeDTO getWorkingTimes() {
		return workingTimes;
	}

	public void setWorkingTimes(AddWorkingTimeDTO workingTimes) {
		this.workingTimes = workingTimes;
	}

}
