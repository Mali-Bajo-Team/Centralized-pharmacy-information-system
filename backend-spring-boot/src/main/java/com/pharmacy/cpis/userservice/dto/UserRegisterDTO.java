package com.pharmacy.cpis.userservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.pharmacy.cpis.util.validators.Numeric;

public class UserRegisterDTO {

	@NotEmpty(message = "Email is required.")
	@Email(message = "Email format is not valid.")
	private String email;
	@NotEmpty(message = "Password is required.")
	@Size(min = 6, message = "Password must have at least 6 characters.")
	private String password;
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
	private String mobile;

	public UserRegisterDTO() {

	}

	public UserRegisterDTO(String email, String password, String name, String surname, String city, String address,
			String country, String mobile) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.city = city;
		this.address = address;
		this.country = country;
		this.mobile = mobile;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getCity() {
		return city;
	}

	public String getAddress() {
		return address;
	}

	public String getCountry() {
		return country;
	}

	public String getMobile() {
		return mobile;
	}
}
