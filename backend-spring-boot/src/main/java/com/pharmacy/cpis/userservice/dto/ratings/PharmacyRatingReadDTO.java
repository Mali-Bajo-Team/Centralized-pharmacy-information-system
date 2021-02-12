package com.pharmacy.cpis.userservice.dto.ratings;

import com.pharmacy.cpis.userservice.model.ratings.PharmacyRating;

public class PharmacyRatingReadDTO {
    private Long id;
    private Integer rating;
    private Long patientId;
    private Long pharmacyId;
    private String pharmacyName;
    private String pharmacyLocation;

    public PharmacyRatingReadDTO(){

    }

    public PharmacyRatingReadDTO(PharmacyRating pharmacyRating){
        this.setId(pharmacyRating.getId());
        this.setRating(pharmacyRating.getRating().getRating());
        this.setPatientId(pharmacyRating.getPatient().getId());
        this.setPharmacyId(pharmacyRating.getPharmacy().getId());
        this.setPharmacyName(pharmacyRating.getPharmacy().getName());
        this.setPharmacyLocation(pharmacyRating.getPharmacy().getLocation().getStreet() + ", " + pharmacyRating.getPharmacy().getLocation().getCity());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyLocation() {
        return pharmacyLocation;
    }

    public void setPharmacyLocation(String pharmacyLocation) {
        this.pharmacyLocation = pharmacyLocation;
    }
}
