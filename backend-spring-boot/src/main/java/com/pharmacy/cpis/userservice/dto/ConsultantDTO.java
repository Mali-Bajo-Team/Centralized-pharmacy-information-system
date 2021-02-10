package com.pharmacy.cpis.userservice.dto;

import com.pharmacy.cpis.userservice.model.users.Consultant;

public class ConsultantDTO {
    private Long consultationId;
    private String email;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String location;
    private String city;
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ConsultantDTO(Long consultationId, String email, String name, String lastName, String phoneNumber, String location, String city, String country) {
        this.consultationId = consultationId;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.city = city;
        this.country = country;
    }

    public ConsultantDTO(){

    }

    public ConsultantDTO(Consultant consultant){
        this.setEmail(consultant.getAccount().getEmail());
        this.setName(consultant.getName());
        this.setLastName(consultant.getSurname());
        this.setPhoneNumber(consultant.getPhoneNumber());
        this.setLocation(consultant.getAddress() + ", " + consultant.getCity());
        this.setCity(consultant.getCity());
        this.setCountry(consultant.getCountry());
    }

    public ConsultantDTO(String email,String name,String lastName,String phoneNumber,String location,String city,String country, Long consultationId) {
        this.email = email;this.name = name;this.lastName = lastName;this.phoneNumber = phoneNumber;this.location = location;  this.city = city;this.country = country; this.consultationId=consultationId;
    }
    public void setEmail(String email,String name,String lastName,String phoneNumber,String location,String city,String country) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.city = city;
        this.country = country;
    }

    public void setEmail(String email) {this.email = email; }

    public String getEmail() {
        return email;
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

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }
}
