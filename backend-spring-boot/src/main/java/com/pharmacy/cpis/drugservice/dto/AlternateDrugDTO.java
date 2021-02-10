package com.pharmacy.cpis.drugservice.dto;

import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.drugservice.service.IDrugService;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

public class AlternateDrugDTO {
    @NotEmpty(message = "Name is required.")
    private String name;

    private String code;

    private String loyaltyPoints;

    private String typeOfDrug;

    private DrugSpecificationDTO drugSpecificationDTO;

    private Double mark;

    public AlternateDrugDTO(){}

    public AlternateDrugDTO(@NotEmpty(message = "Name is required.") String name, String code, String loyaltyPoints, String typeOfDrug, DrugSpecificationDTO drugSpecificationDTO, Double mark) {
        this.name = name;
        this.code = code;
        this.loyaltyPoints = loyaltyPoints;
        this.typeOfDrug = typeOfDrug;
        this.drugSpecificationDTO = drugSpecificationDTO;
        this.mark = mark;
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


}
