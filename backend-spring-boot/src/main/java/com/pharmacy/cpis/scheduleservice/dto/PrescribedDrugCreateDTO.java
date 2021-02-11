package com.pharmacy.cpis.scheduleservice.dto;

public class PrescribedDrugCreateDTO {
    private Integer amount;
    private String drugCode;

    public PrescribedDrugCreateDTO(){}

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }
}
