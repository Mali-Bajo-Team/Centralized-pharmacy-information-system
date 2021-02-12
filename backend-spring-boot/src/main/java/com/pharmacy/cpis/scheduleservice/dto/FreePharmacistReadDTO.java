package com.pharmacy.cpis.scheduleservice.dto;

import com.pharmacy.cpis.userservice.model.users.Consultant;

public class FreePharmacistReadDTO {
    private Long consultantId;
    private Double consultantRating;
    private String consultantName;
    private String consultantSurname;

    public FreePharmacistReadDTO(){}

    public FreePharmacistReadDTO(Consultant consultant){
        this.setConsultantId(consultant.getId());
        this.setConsultantName(consultant.getName());
        this.setConsultantSurname(consultant.getSurname());
        this.setConsultantRating(consultant.getAverageRating());
    }

    public Long getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }

    public Double getConsultantRating() {
        return consultantRating;
    }

    public void setConsultantRating(Double consultantRating) {
        this.consultantRating = consultantRating;
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
