package com.pharmacy.cpis.pharmacyservice.dto.statistics;

import javax.validation.constraints.NotNull;

public class YearRangeDTO {
	@NotNull(message = "Start year can not be empty.")
	private int start;
	@NotNull(message = "End year can not be empty.")
	private int end;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}