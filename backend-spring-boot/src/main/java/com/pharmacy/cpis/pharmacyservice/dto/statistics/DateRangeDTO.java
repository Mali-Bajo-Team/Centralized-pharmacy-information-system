package com.pharmacy.cpis.pharmacyservice.dto.statistics;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class DateRangeDTO {
	@NotNull(message = "Start date is required.")
	private Date start;
	@NotNull(message = "End date is required.")
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
