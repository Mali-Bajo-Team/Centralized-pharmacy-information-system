package com.pharmacy.cpis.userservice.dto.ratings;

public class ConsultantRatingCreateDTO {
    private Integer rating;
    private Long consultantId;
    private String patientEmail;

    public ConsultantRatingCreateDTO(){}

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
}
