package com.pharmacy.cpis.drugservice.dto;

import java.util.Date;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddDrugOrderDTO {

	@NotNull(message = "Deadline is required.")
	@Future(message = "Deadline must be in the future.")
	private Date deadline;

	@NotEmpty(message = "List of ordered drugs cannot be empty.")
	private Set<@Valid AddOrderedDrugDTO> orderedDrugs;

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Set<AddOrderedDrugDTO> getOrderedDrugs() {
		return orderedDrugs;
	}

	public void setOrderedDrugs(Set<@Valid AddOrderedDrugDTO> orderedDrugs) {
		this.orderedDrugs = orderedDrugs;
	}

}
