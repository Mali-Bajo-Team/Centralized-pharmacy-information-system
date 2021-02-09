package com.pharmacy.cpis.drugservice.dto;

import java.util.Date;

import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugRequest;

public class DrugRequestDTO {

	private Date timestamp;
	private String consultantName;
	private String consultantSurname;
	private String drugCode;
	private String drugName;

	public DrugRequestDTO(DrugRequest drugRequest) {
		super();
		this.timestamp = drugRequest.getTimestamp();
		this.consultantName = drugRequest.getConsultant().getName();
		this.consultantSurname = drugRequest.getConsultant().getSurname();
		this.drugCode = drugRequest.getDrug().getCode();
		this.drugName = drugRequest.getDrug().getName();
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getConsultantName() {
		return consultantName;
	}

	public String getConsultantSurname() {
		return consultantSurname;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

}
