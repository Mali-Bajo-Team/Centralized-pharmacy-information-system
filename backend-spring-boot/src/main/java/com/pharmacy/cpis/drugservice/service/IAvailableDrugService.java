package com.pharmacy.cpis.drugservice.service;

import com.pharmacy.cpis.drugservice.dto.AddAvailableDrugDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;

public interface IAvailableDrugService {

	Iterable<AvailableDrug> getByPharmacy(Long pharmacyId);

	AvailableDrug addToPharmacy(Long pharmacyId, AddAvailableDrugDTO drugInfo);

	void deleteFromPharmacy(Long pharmacyId, String drugCode);

	AvailableDrug getByPharmacyAndDrug(Long pharmacyId, String drugCode);

}
