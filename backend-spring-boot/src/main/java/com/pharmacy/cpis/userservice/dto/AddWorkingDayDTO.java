package com.pharmacy.cpis.userservice.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class AddWorkingDayDTO {

	@NotNull(message = "Start of shift is required.")
	private Date start;

	@NotNull(message = "End of shift is required.")
	private Date end;

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}
