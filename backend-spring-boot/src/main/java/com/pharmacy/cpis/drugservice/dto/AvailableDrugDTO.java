package com.pharmacy.cpis.drugservice.dto;

import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;

public class AvailableDrugDTO {

	private String code;
	private String name;
	private String drugForm;
	private String drugClass;
	private Integer amount;

	public AvailableDrugDTO(AvailableDrug availableDrug) {
		super();
		this.code = availableDrug.getDrug().getCode();
		this.name = availableDrug.getDrug().getName();
		this.drugForm = availableDrug.getDrug().getDrugForm().getName();
		this.drugClass = availableDrug.getDrug().getDrugClass().getName();
		this.amount = availableDrug.getAvailableAmount();
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getDrugForm() {
		return drugForm;
	}

	public String getDrugClass() {
		return drugClass;
	}

	public Integer getAmount() {
		return amount;
	}

}
