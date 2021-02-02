package com.pharmacy.cpis.userservice.dto;

public class UserRegisterDTO {

    private String email;
    private String password;
    private String name;
    private String surname;
    private String city;
    private String address;
    private String country;
    private String mobile;

    public UserRegisterDTO(){

    }

    public UserRegisterDTO(String email, String password, String name, String surname, String city, String address, String country, String mobile) {
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
