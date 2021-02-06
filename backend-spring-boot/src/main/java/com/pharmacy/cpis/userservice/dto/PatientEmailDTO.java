package com.pharmacy.cpis.userservice.dto;

import javax.validation.constraints.NotEmpty;

public class PatientEmailDTO {

    @NotEmpty(message = "Email is required.")
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
