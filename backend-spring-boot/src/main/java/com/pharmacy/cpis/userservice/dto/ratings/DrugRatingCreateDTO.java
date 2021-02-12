package com.pharmacy.cpis.userservice.dto.ratings;

public class DrugRatingCreateDTO {
    private Integer rating;
    private String drugCode;
    private String patientEmail;

    public DrugRatingCreateDTO(){}

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
}
