package com.pharmacy.cpis.drugservice.dto;

import com.pharmacy.cpis.drugservice.model.drug.Drug;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DrugDTO {
    @NotEmpty(message = "Name is required.")
    private String name;

    @NotEmpty(message = "Code is required.")
    private String code;

    @NotEmpty(message = "Loyalty points is required.")
    private String loyaltyPoints;

    @NotEmpty(message = "Type of drug is required.")
    private String typeOfDrug;

    private List<Drug> alternateDrugs;

    public DrugDTO(){}

    public DrugDTO(@NotEmpty(message = "Name is required.") String name, @NotEmpty(message = "Code is required.") String code, @NotEmpty(message = "Loyalty points is required.") String loyaltyPoints, @NotEmpty(message = "Type of drug is required.") String typeOfDrug, List<Drug> alternateDrugs) {
        this.name = name;
        this.code = code;
        this.loyaltyPoints = loyaltyPoints;
        this.typeOfDrug = typeOfDrug;
        this.alternateDrugs = alternateDrugs;
    }

    public DrugDTO(Drug drug){
        this.setName(drug.getName());
        this.setCode(drug.getCode());
        this.setAlternateDrugs(getAlternateDrugs(drug.getAlternateDrugs()));
        this.setLoyaltyPoints(drug.getLoyaltyPoints().toString());
        this.setTypeOfDrug(drug.getDrugClass().getName());
    }

    /**
     * Convert algernate drugs in Hash to List form
     * @param alternateDrugs
     * @return
     */
    private List<Drug> getAlternateDrugs(Set<Drug> alternateDrugs){
        List<Drug> drugList = new ArrayList<>();
        for(Drug drug : alternateDrugs)
            drugList.add(drug);
        return drugList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(String loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getTypeOfDrug() {
        return typeOfDrug;
    }

    public void setTypeOfDrug(String typeOfDrug) {
        this.typeOfDrug = typeOfDrug;
    }

    public List<Drug> getAlternateDrugs() {
        return alternateDrugs;
    }

    public void setAlternateDrugs(List<Drug> alternateDrugs) {
        this.alternateDrugs = alternateDrugs;
    }
}
