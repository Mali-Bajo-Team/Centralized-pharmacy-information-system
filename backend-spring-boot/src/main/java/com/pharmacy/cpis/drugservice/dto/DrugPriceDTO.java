package com.pharmacy.cpis.drugservice.dto;

import java.util.Date;

public class DrugPriceDTO {

	private Date date;
	private Double price;

	public DrugPriceDTO() {
		super();
	}

	public DrugPriceDTO(Date date, Double price) {
		super();
		this.date = date;
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
