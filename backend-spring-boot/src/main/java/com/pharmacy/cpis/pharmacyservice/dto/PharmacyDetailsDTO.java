package com.pharmacy.cpis.pharmacyservice.dto;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;

public class PharmacyDetailsDTO {

	private String name;
	private String description;
	private String address;
	private Double latitude;
	private Double longitude;
	private Double rating;

	public PharmacyDetailsDTO(Pharmacy pharmacy) {
		super();
		this.name = pharmacy.getName();
		this.description = pharmacy.getDescription();
		this.latitude = pharmacy.getLocation().getLatitude();
		this.longitude = pharmacy.getLocation().getLongitude();
		this.address = pharmacy.getLocation().toString();
		this.rating = pharmacy.getAverageRating();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getAddress() {
		return address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Double getRating() {
		return rating;
	}

}
