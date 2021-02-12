package com.pharmacy.cpis.userservice.dto.ratings;

import com.pharmacy.cpis.userservice.model.ratings.DrugRating;

public class DrugRatingReadDTO {
    private Long id;
    private Integer rating;
    private String drugCode;
    private String drugName;
    private Long patientId;


    public DrugRatingReadDTO(){}

    public DrugRatingReadDTO(DrugRating drugRating){
        this.setId(drugRating.getId());
        this.setRating(drugRating.getRating().getRating());
        this.setDrugCode(drugRating.getDrug().getCode());
        this.setDrugName(drugRating.getDrug().getName());
        this.setPatientId(drugRating.getPatient().getId());
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

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
