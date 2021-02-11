package com.pharmacy.cpis.userservice.dto.ratings;

import com.pharmacy.cpis.userservice.model.ratings.ConsultantRating;

public class ConsultantRatingReadDTO {
    private Long id;
    private Integer rating;
    private Long patientId;
    private Long consultantId;
    private String consultantName;
    private String consultantSurname;

    public ConsultantRatingReadDTO(){}

    public ConsultantRatingReadDTO(ConsultantRating consultantRating){
        this.setId(consultantRating.getId());
        this.setRating(consultantRating.getRating().getRating());
        this.setPatientId(consultantRating.getPatient().getId());
        this.setConsultantId(consultantRating.getConsultant().getId());
        this.setConsultantName(consultantRating.getConsultant().getName());
        this.setConsultantSurname(consultantRating.getConsultant().getSurname());
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

    public Long getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public String getConsultantSurname() {
        return consultantSurname;
    }

    public void setConsultantSurname(String consultantSurname) {
        this.consultantSurname = consultantSurname;
    }
}
