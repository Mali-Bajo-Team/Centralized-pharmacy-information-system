package com.pharmacy.cpis.drugservice.dto;

import com.pharmacy.cpis.drugservice.model.drugprocurement.OrderedDrug;

public class OrderedDrugDTO {
    private Integer amount;
    private DrugDTO drug;

    public OrderedDrugDTO(){}

    public OrderedDrugDTO(OrderedDrug orderedDrug){
        this.setAmount(orderedDrug.getAmount());
        this.setDrug(new DrugDTO(orderedDrug.getDrug()));
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public DrugDTO getDrug() {
        return drug;
    }

    public void setDrug(DrugDTO drug) {
        this.drug = drug;
    }
}
