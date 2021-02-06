package com.pharmacy.cpis.drugservice.dto;

import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.drugservice.model.drug.DrugSpecification;
import com.pharmacy.cpis.drugservice.service.IDrugService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
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

    private List<DrugDTO> alternateDrugs;

    private DrugSpecificationDTO drugSpecificationDTO;

    private Double mark;

    public DrugDTO(){}

    public DrugDTO(@NotEmpty(message = "Name is required.") String name, @NotEmpty(message = "Code is required.") String code, @NotEmpty(message = "Loyalty points is required.") String loyaltyPoints, @NotEmpty(message = "Type of drug is required.") String typeOfDrug, List<DrugDTO> alternateDrugs, Double mark) {
        this.name = name;
        this.code = code;
        this.loyaltyPoints = loyaltyPoints;
        this.typeOfDrug = typeOfDrug;
        this.alternateDrugs = alternateDrugs;
        this.mark = mark;
    }

    public DrugDTO(Drug drug, IDrugService drugService){
        this.setName(drug.getName());
        this.setCode(drug.getCode());
        this.setLoyaltyPoints(drug.getLoyaltyPoints().toString());
        this.setTypeOfDrug(drug.getDrugClass().getName());
        this.setMark(drugService.getMarkOfDrug(drug));
        this.setAlternateDrugs(drug.getAlternateDrugs(),drugService);
        this.setDrugSpecificationDTO(new DrugSpecificationDTO(drugService.getDrugSpecificationByDrugCode(drug.getCode())));
    }

    public DrugSpecificationDTO getDrugSpecificationDTO() {
        return drugSpecificationDTO;
    }

    public void setDrugSpecificationDTO(DrugSpecificationDTO drugSpecificationDTO) {
        this.drugSpecificationDTO = drugSpecificationDTO;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
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

    public List<DrugDTO> getAlternateDrugs() {
        return alternateDrugs;
    }

    public void setAlternateDrugs(Set<Drug> alternateDrugs, IDrugService drugService) {
        for(Drug drug: alternateDrugs){
            DrugDTO drugDTO = new DrugDTO();
            drugDTO.setName(drug.getName());
            drugDTO.setCode(drug.getCode());
            drugDTO.setLoyaltyPoints(drug.getLoyaltyPoints().toString());
            drugDTO.setTypeOfDrug(drug.getDrugClass().getName());
            drugDTO.setMark(drugService.getMarkOfDrug(drug));
            this.alternateDrugs.add(drugDTO);
        }
    }
}
