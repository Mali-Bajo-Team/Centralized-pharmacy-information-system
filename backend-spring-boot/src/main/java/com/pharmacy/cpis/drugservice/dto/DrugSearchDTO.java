package com.pharmacy.cpis.drugservice.dto;

public class DrugSearchDTO {

	private String name;
	private String code;
	private Long drugFormId;
	private Long drugClassId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getDrugFormId() {
		return drugFormId;
	}

	public void setDrugFormId(Long drugFormId) {
		this.drugFormId = drugFormId;
	}

	public Long getDrugClassId() {
		return drugClassId;
	}

	public void setDrugClassId(Long drugClassId) {
		this.drugClassId = drugClassId;
	}

}
