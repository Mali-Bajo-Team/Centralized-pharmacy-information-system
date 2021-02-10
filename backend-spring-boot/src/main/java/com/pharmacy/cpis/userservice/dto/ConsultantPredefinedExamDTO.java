package com.pharmacy.cpis.userservice.dto;

public class ConsultantPredefinedExamDTO {
    private Long consultationID;
    private String email;

    public ConsultantPredefinedExamDTO() {
    }

    public ConsultantPredefinedExamDTO(Long consultationID, String email) {
        this.consultationID = consultationID;
        this.email = email;
    }

    public Long getConsultationID() {
        return consultationID;
    }

    public void setConsultationID(Long consultationID) {
        this.consultationID = consultationID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
