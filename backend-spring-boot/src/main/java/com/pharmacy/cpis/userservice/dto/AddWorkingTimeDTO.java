package com.pharmacy.cpis.userservice.dto;

import javax.validation.Valid;

public class AddWorkingTimeDTO {

	@Valid
	private AddWorkingDayDTO monday;
	@Valid
	private AddWorkingDayDTO tuesday;
	@Valid
	private AddWorkingDayDTO wednesday;
	@Valid
	private AddWorkingDayDTO thursday;
	@Valid
	private AddWorkingDayDTO friday;
	@Valid
	private AddWorkingDayDTO saturday;
	@Valid
	private AddWorkingDayDTO sunday;

	public AddWorkingDayDTO getMonday() {
		return monday;
	}

	public void setMonday(AddWorkingDayDTO monday) {
		this.monday = monday;
	}

	public AddWorkingDayDTO getTuesday() {
		return tuesday;
	}

	public void setTuesday(AddWorkingDayDTO tuesday) {
		this.tuesday = tuesday;
	}

	public AddWorkingDayDTO getWednesday() {
		return wednesday;
	}

	public void setWednesday(AddWorkingDayDTO wednesday) {
		this.wednesday = wednesday;
	}

	public AddWorkingDayDTO getThursday() {
		return thursday;
	}

	public void setThursday(AddWorkingDayDTO thursday) {
		this.thursday = thursday;
	}

	public AddWorkingDayDTO getFriday() {
		return friday;
	}

	public void setFriday(AddWorkingDayDTO friday) {
		this.friday = friday;
	}

	public AddWorkingDayDTO getSaturday() {
		return saturday;
	}

	public void setSaturday(AddWorkingDayDTO saturday) {
		this.saturday = saturday;
	}

	public AddWorkingDayDTO getSunday() {
		return sunday;
	}

	public void setSunday(AddWorkingDayDTO sunday) {
		this.sunday = sunday;
	}

}
