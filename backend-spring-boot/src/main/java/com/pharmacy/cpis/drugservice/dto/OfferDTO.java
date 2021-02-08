package com.pharmacy.cpis.drugservice.dto;

import java.util.Date;

import com.pharmacy.cpis.drugservice.model.drugprocurement.Offer;

public class OfferDTO {

	private Long id;

	private Date shipmentDate;

	private Double price;

	public OfferDTO(Offer offer) {
		super();
		this.id = offer.getId();
		this.shipmentDate = offer.getShipmentDate();
		this.price = offer.getPrice();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(Date shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
