package com.pharmacy.cpis.userservice.dto.ratings;

import com.pharmacy.cpis.userservice.model.ratings.PharmacyRating;

public class PharmacyRatingCreateDTO {
    private Integer rating;
    private String patientEmail;
    private Long pharmacyId;

    public PharmacyRatingCreateDTO(){}

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
}
