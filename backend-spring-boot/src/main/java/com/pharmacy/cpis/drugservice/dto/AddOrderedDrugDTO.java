package com.pharmacy.cpis.drugservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddOrderedDrugDTO {

	@NotEmpty(message = "Ordered drug code is required.")
	private String code;

	@NotNull(message = "Amount of ordered drug is required.")
	@Min(value = 1, message = "Amount of ordered drug must be positive.")
	private Integer amount;

	@NotNull(message = "Default price of ordered drug is required.")
	@Min(value = 0, message = "Default price of ordered drug must be positive.")
	private Double defaultPrice;

	public AddOrderedDrugDTO() {
		super();
	}

	public AddOrderedDrugDTO(@NotEmpty(message = "Ordered drug code is required.") String code,
			@NotNull(message = "Amount of ordered drug is required.") @Min(value = 1, message = "Amount of ordered drug must be positive.") Integer amount,
			@NotNull(message = "Default price of ordered drug is required.") @Min(value = 0, message = "Default price of ordered drug must be positive.") Double defaultPrice) {
		super();
		this.code = code;
		this.amount = amount;
		this.defaultPrice = defaultPrice;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(Double defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddOrderedDrugDTO other = (AddOrderedDrugDTO) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

}
