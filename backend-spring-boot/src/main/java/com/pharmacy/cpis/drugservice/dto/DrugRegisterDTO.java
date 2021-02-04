package com.pharmacy.cpis.drugservice.dto;

import com.pharmacy.cpis.drugservice.model.drug.DrugSpecification;

import javax.validation.constraints.NotEmpty;

public class DrugRegisterDTO {
    private DrugDTO drug;
    private DrugSpecificationDTO specification;

    public DrugRegisterDTO(){}

    public DrugRegisterDTO(DrugDTO drug, DrugSpecificationDTO specification) {
        this.drug = drug;
        this.specification = specification;
    }

    public DrugDTO getDrug() {
        return drug;
    }

    public void setDrug(DrugDTO drug) {
        this.drug = drug;
    }

    public DrugSpecificationDTO getSpecification() {
        return specification;
    }

    public void setSpecification(DrugSpecificationDTO specification) {
        this.specification = specification;
    }
}
