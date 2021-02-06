package com.pharmacy.cpis.pharmacyservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PharmacyUpdateDTO {

	@NotEmpty(message = "Name is required.")
	private String name;

	@NotEmpty(message = "Description is required.")
	@Size(min = 6, message = "Description must have at least 6 characters.")
	private String description;

	@NotNull(message = "Dermatologist consultation price  is required.")
	@Min(value = 0, message = "Dermatologist consultation price must be positive.")
	private Double dermatologistConsultationPrice;

	@NotNull(message = "Pharmacist consultation price is required.")
	@Min(value = 0, message = "Pharmacist consultation price must be positive.")
	private Double pharmacistConsultationPrice;

	@NotEmpty(message = "Street is required.")
	private String street;

	@NotEmpty(message = "City is required.")
	private String city;

	@NotEmpty(message = "Country is required.")
	private String country;

	@NotNull(message = "Latitude is required.")
	private Double latitude;

	@NotNull(message = "Longitude is required.")
	private Double longitude;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getDermatologistConsultationPrice() {
		return dermatologistConsultationPrice;
	}

	public void setDermatologistConsultationPrice(Double dermatologistConsultationPrice) {
		this.dermatologistConsultationPrice = dermatologistConsultationPrice;
	}

	public Double getPharmacistConsultationPrice() {
		return pharmacistConsultationPrice;
	}

	public void setPharmacistConsultationPrice(Double pharmacistConsultationPrice) {
		this.pharmacistConsultationPrice = pharmacistConsultationPrice;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
