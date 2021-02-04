package com.pharmacy.cpis.userservice.dto;

import javax.validation.constraints.NotEmpty;

import com.pharmacy.cpis.userservice.model.users.Person;
import com.pharmacy.cpis.util.validators.Numeric;

public class PersonalInformationDTO {

	@NotEmpty(message = "Name is required.")
	private String name;
	@NotEmpty(message = "Surname is required.")
	private String surname;
	@NotEmpty(message = "City is required.")
	private String city;
	@NotEmpty(message = "Address is required.")
	private String address;
	@NotEmpty(message = "Country is required.")
	private String country;
	@NotEmpty(message = "Phone number is required.")
	@Numeric(message = "Phone number can only contain numbers.")
	private String phone;

	public PersonalInformationDTO() {
		super();
	}

	public PersonalInformationDTO(Person person) {
		super();
		this.name = person.getName();
		this.surname = person.getSurname();
		this.address = person.getAddress();
		this.city = person.getCity();
		this.country = person.getCountry();
		this.phone = person.getPhoneNumber();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
