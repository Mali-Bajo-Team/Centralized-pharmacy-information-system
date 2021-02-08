package com.pharmacy.cpis.drugservice.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddDrugPriceDTO {

	@NotNull(message = "Start date is required.")
	@Future(message = "Start date must be in the future.")
	Date start;

	@NotNull(message = "End date is required.")
	@Future(message = "End date must be in the future.")
	Date end;

	@NotNull(message = "Price is required.")
	@Min(value = 0, message = "Price must be positive.")
	Double price;

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
