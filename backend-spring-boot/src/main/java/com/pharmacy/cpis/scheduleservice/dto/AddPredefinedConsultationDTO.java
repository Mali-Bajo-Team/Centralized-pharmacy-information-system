package com.pharmacy.cpis.scheduleservice.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddPredefinedConsultationDTO {

	@NotNull(message = "Consultant id is required.")
	private Long consultantId;

	@NotNull(message = "Start time is required.")
	@Future(message = "Start time must be in the future.")
	private Date start;

	@NotNull(message = "End time is required.")
	@Future(message = "End time must be in the future.")
	private Date end;

	@Min(value = 0, message = "Price must be positive.")
	private Double price;

	public Long getConsultantId() {
		return consultantId;
	}

	public void setConsultantId(Long consultantId) {
		this.consultantId = consultantId;
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
