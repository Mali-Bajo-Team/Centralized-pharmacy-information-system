package com.pharmacy.cpis.pharmacyservice.model.pharmacy;

import javax.persistence.Embeddable;

@Embeddable
public class Location {
	private String street;
	private Integer houseNumber;
	private String city;
	private String country;
	private Double latitude;
	private Double longitude;

	public Location() {
		super();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
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

	@Override
	public String toString() {
		return street + " " + houseNumber + ", " + city + ", " + country;
	}

}
