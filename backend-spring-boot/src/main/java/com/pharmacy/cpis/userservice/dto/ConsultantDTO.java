package com.pharmacy.cpis.userservice.dto;

public class ConsultantDTO {
    private String email;

    public ConsultantDTO(){

    }

    public ConsultantDTO(String email) {
        this.email = email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
