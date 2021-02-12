package com.pharmacy.cpis.pharmacyservice.dto;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.util.CollectionUtil;

public class PharmacyDetailsDTO {

	private String name;
	private String description;
	private String address;
	private Double latitude;
	private Double longitude;
	private Double rating;
	private Double dermatologistConsultationPrice;
	private Double pharmacistConsultationPrice;
	private Boolean subscribed;

	public PharmacyDetailsDTO(Pharmacy pharmacy) {
		super();
		this.name = pharmacy.getName();
		this.description = pharmacy.getDescription();
		this.latitude = pharmacy.getLocation().getLatitude();
		this.longitude = pharmacy.getLocation().getLongitude();
		this.address = pharmacy.getLocation().getStreet() + ", " + pharmacy.getLocation().getCity() + ", "
				+ pharmacy.getLocation().getCountry();
		this.rating = pharmacy.getAverageRating();
		this.dermatologistConsultationPrice = pharmacy.getDermatologistConsultationPrice();
		this.pharmacistConsultationPrice = pharmacy.getPharmacistConsultationPrice();
	}

	public PharmacyDetailsDTO(Pharmacy pharmacy, String patientEmail) {
		this(pharmacy);
		this.subscribed = CollectionUtil.contains(pharmacy.getSubscribers(),
				sub -> sub.getAccount().getEmail().equals(patientEmail));
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

	public Double getDermatologistConsultationPrice() {
		return dermatologistConsultationPrice;
	}

	public Double getPharmacistConsultationPrice() {
		return pharmacistConsultationPrice;
	}

	public Boolean getSubscribed() {
		return subscribed;
	}

}
