package com.pharmacy.cpis.drugservice.dto;

public class DrugCodeAndAmountDTO {
    private String drugCode;
    private Integer amount;

    public DrugCodeAndAmountDTO(){}

    public DrugCodeAndAmountDTO(String drugCode, Integer amount) {
        this.drugCode = drugCode;
        this.amount = amount;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
