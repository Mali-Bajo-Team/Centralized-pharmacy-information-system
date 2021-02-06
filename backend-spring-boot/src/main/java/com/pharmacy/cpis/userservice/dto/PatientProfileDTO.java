package com.pharmacy.cpis.userservice.dto;

public class PatientProfileDTO {

    private String name;
    private String surname;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private String email;
    private Integer loyaltyPoints;
    private UserCategoryDTO userCategoryDTO;

    public PatientProfileDTO() {

    }

    public PatientProfileDTO(String name, String surname, String phoneNumber, String address, String city, String country, String email, Integer loyaltyPoints, UserCategoryDTO userCategoryDTO) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.loyaltyPoints = loyaltyPoints;
        this.userCategoryDTO = userCategoryDTO;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public UserCategoryDTO getUserCategoryDTO() {
        return userCategoryDTO;
    }

    public void setUserCategoryDTO(UserCategoryDTO userCategoryDTO) {
        this.userCategoryDTO = userCategoryDTO;
    }
}
