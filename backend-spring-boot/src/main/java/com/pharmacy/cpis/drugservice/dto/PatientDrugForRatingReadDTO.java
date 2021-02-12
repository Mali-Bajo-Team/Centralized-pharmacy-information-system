package com.pharmacy.cpis.drugservice.dto;

import com.pharmacy.cpis.drugservice.model.drug.Drug;

public class PatientDrugForRatingReadDTO {
    private String drugCode;
    private String drugName;

    public PatientDrugForRatingReadDTO(Drug drug){
        this.setDrugCode(drug.getCode());
        this.setDrugName(drug.getName());
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
}
