package com.pharmacy.cpis.scheduleservice.dto;

public class PatientCancelConsultationDTO {
    private String email;
    private Long consultationId;

    public PatientCancelConsultationDTO(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }
}
