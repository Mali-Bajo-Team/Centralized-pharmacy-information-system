package com.pharmacy.cpis.userservice.dto;

public class ConsultantDTO {
    private String email;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String location;

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

    public ConsultantDTO(){

    }

    public ConsultantDTO(String email) {
        this.email = email;
    }
    public void setEmail(String email) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    public String getEmail() {
        return email;
    }
}
