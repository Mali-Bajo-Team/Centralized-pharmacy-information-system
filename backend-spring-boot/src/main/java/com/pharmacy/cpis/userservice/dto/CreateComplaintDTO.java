package com.pharmacy.cpis.userservice.dto;

public class CreateComplaintDTO {
    private String content;
    private String consultantEmail;
    private Long pharmacyId;
    private String patientEmail;

    public CreateComplaintDTO(){}

    public String getPatientEmail() {
        return patientEmail;
    }

    public String getConsultantEmail() {
        return consultantEmail;
    }

    public void setConsultantEmail(String consultantEmail) {
        this.consultantEmail = consultantEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
}
