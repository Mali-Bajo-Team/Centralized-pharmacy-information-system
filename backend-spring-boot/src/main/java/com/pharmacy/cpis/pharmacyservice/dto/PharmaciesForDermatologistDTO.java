package com.pharmacy.cpis.pharmacyservice.dto;

public class PharmaciesForDermatologistDTO {

    String dermatologistEmail;

    public PharmaciesForDermatologistDTO() {
    }

    public PharmaciesForDermatologistDTO(String dermatologistEmail) {
        this.dermatologistEmail = dermatologistEmail;
    }

    public String getDermatologistEmail() {
        return dermatologistEmail;
    }

    public void setDermatologistEmail(String dermatologistEmail) {
        this.dermatologistEmail = dermatologistEmail;
    }
}