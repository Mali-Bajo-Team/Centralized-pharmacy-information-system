package com.pharmacy.cpis.drugservice.dto;

public class DrugWithoutAlergiesDTO {
    private Long paatientID;

    public DrugWithoutAlergiesDTO() {
    }

    public DrugWithoutAlergiesDTO(Long paatientID) {
        this.paatientID = paatientID;
    }

    public Long getPaatientID() {
        return paatientID;
    }

    public void setPaatientID(Long paatientID) {
        this.paatientID = paatientID;
    }
}
