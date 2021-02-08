package com.pharmacy.cpis.drugservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddAvailableDrugDTO {

	@NotEmpty(message = "Drug code is required.")
	private String code;

	@NotNull(message = "Default price is required.")
	@Min(value = 0, message = "Default price must be positive.")
	private Double defaultPrice;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(Double defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

}
