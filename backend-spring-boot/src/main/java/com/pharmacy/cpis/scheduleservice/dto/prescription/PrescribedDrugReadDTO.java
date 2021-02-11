package com.pharmacy.cpis.scheduleservice.dto.prescription;

import com.pharmacy.cpis.scheduleservice.model.prescriptions.PrescribedDrug;

public class PrescribedDrugReadDTO {
    private Long id;
    private Integer amount;
    private String drugCode;

    public PrescribedDrugReadDTO(){
    }

    public PrescribedDrugReadDTO(PrescribedDrug prescribedDrug){
        this.setId(prescribedDrug.getId());
        this.setAmount(prescribedDrug.getAmount());
        this.setDrugCode(prescribedDrug.getDrug().getCode());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
