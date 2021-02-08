package com.pharmacy.cpis.drugservice.service;

import java.util.Collection;

import com.pharmacy.cpis.drugservice.dto.AddAvailableDrugDTO;
import com.pharmacy.cpis.drugservice.dto.DrugSearchDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;

public interface IAvailableDrugService {

	Collection<AvailableDrug> getByPharmacy(Long pharmacyId);

	public Collection<AvailableDrug> searchByPharmacy(Long pharmacyId, DrugSearchDTO searchDTO);

	AvailableDrug addToPharmacy(Long pharmacyId, AddAvailableDrugDTO drugInfo);

	void deleteFromPharmacy(Long pharmacyId, String drugCode);

	AvailableDrug getByPharmacyAndDrug(Long pharmacyId, String drugCode);

}
