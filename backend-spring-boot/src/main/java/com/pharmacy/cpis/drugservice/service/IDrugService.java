package com.pharmacy.cpis.drugservice.service;

import java.util.Collection;
import java.util.List;

import com.pharmacy.cpis.drugservice.dto.DrugDTO;
import com.pharmacy.cpis.drugservice.dto.DrugRegisterDTO;
import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.drugservice.model.drug.DrugClass;
import com.pharmacy.cpis.drugservice.model.drug.DrugForm;
import com.pharmacy.cpis.drugservice.model.drug.DrugSpecification;
import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;

public interface IDrugService {

	Drug registerDrug(DrugRegisterDTO drug);

	List<Drug> findAll();

	List<DrugClass> findAllDrugClass();

	Double getMarkOfDrug(Drug drug);

	DrugSpecification getDrugSpecificationByDrugCode(String drugCode);

	List<AvailableDrug> findAvailableDrugsByCode(String drugCode);

	Collection<DrugForm> findAllDrugForms();

	List<DrugDTO> getDrugsForPhatientWithoutAlergies(Long paatientID, IDrugService drugService);
}
