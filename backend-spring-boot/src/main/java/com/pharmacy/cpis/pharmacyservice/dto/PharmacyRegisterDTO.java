package com.pharmacy.cpis.pharmacyservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;

public class PharmacyRegisterDTO {

	@NotEmpty(message = "Name is required.")
	private String name;

	// TODO: Check is location changed on front(pharmacy registration) to this
	// format (country, address, city)
	@NotEmpty(message = "City is required.")
	private String city;
	@NotEmpty(message = "Address is required.")
	private String address;
	@NotEmpty(message = "Country is required.")
	private String country;

	@NotEmpty(message = "Description is required.")
	@Size(min = 6, message = "Description must have at least 6 characters.")
	private String description;

	@NotNull(message = "Dermatologist consultation price  is required.")
	@Min(value = 0, message = "Dermatologist consultation price must be positive.")
	private Double dermatologistConsultationPrice;

	@NotNull(message = "Pharmacist consultation price is required.")
	@Min(value = 0, message = "Pharmacist consultation price must be positive.")
	private Double pharmacistConsultationPrice;

	@NotNull(message = "Pharmacy administrator is required.")
	private UserRegisterDTO pharmacyAdministrator;

	public PharmacyRegisterDTO() {

	}

	public PharmacyRegisterDTO(@NotEmpty(message = "Name is required.") String name,
			@NotEmpty(message = "City is required.") String city,
			@NotEmpty(message = "Address is required.") String address,
			@NotEmpty(message = "Country is required.") String country,
			@NotEmpty(message = "Description is required.") @Size(min = 6, message = "Description must have at least 6 characters.") String description,
			@NotEmpty(message = "Dermatologist consultation price  is required.") Double dermatologistConsultationPrice,
			@NotEmpty(message = "Pharmacist consultation price is required.") Double pharmacistConsultationPrice,
			@NotEmpty(message = "Pharmacy administrator is required.") UserRegisterDTO pharmacyAdministrator) {
		this.name = name;
		this.city = city;
		this.address = address;
		this.country = country;
		this.description = description;
		this.dermatologistConsultationPrice = dermatologistConsultationPrice;
		this.pharmacistConsultationPrice = pharmacistConsultationPrice;
		this.pharmacyAdministrator = pharmacyAdministrator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public UserRegisterDTO getPharmacyAdministrator() {
		return pharmacyAdministrator;
	}

	public void setPharmacyAdministrator(UserRegisterDTO pharmacyAdministrator) {
		this.pharmacyAdministrator = pharmacyAdministrator;
	}
}
