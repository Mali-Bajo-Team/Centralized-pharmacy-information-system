package com.pharmacy.cpis.userservice.dto;

public class PatientEmailDTO {
    private String email;

    public PatientEmailDTO(){

    }
    public PatientEmailDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
