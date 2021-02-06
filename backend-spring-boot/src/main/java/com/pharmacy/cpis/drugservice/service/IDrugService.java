package com.pharmacy.cpis.drugservice.service;

import com.pharmacy.cpis.drugservice.dto.DrugDTO;
import com.pharmacy.cpis.drugservice.dto.DrugRegisterDTO;
import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.drugservice.model.drug.DrugClass;
import com.pharmacy.cpis.drugservice.model.drug.DrugSpecification;

import java.util.List;

public interface IDrugService {

    Drug registerDrug(DrugRegisterDTO drug);

    List<Drug> findAll();

    List<DrugClass> findAllDrugClass();

    Double getMarkOfDrug(Drug drug);

    DrugSpecification getDrugSpecificationByDrugCode(String drugCode);

}
