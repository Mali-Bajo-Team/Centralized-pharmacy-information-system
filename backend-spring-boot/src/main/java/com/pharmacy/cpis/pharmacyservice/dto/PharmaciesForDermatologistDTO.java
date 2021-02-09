package com.pharmacy.cpis.pharmacyservice.dto;

public class PharmaciesForDermatologistDTO {

    String dermatologistEmail;

    public PharmaciesForDermatologistDTO(String dermatologistEmail) {
        this.dermatologistEmail = dermatologistEmail;
    }

    public PharmaciesForDermatologistDTO() {
    }

    public String getDermatologistEmail() {
        return dermatologistEmail;
    }

    public void setDermatologistEmail(String dermatologistEmail) {
        this.dermatologistEmail = dermatologistEmail;
    }
}
