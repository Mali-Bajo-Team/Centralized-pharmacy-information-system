package com.pharmacy.cpis.drugservice.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.pharmacy.cpis.drugservice.dto.AddAvailableDrugDTO;
import com.pharmacy.cpis.drugservice.dto.AddDrugPriceDTO;
import com.pharmacy.cpis.drugservice.dto.DrugCodeAndAmountDTO;
import com.pharmacy.cpis.drugservice.dto.DrugPriceDTO;
import com.pharmacy.cpis.drugservice.dto.DrugSearchDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;
import com.pharmacy.cpis.pharmacyservice.dto.PharmacyTotalPriceDTO;

public interface IAvailableDrugService {

	Collection<AvailableDrug> getByPharmacyForPatient(Long pharmacyId, String patientEmail);

	Collection<AvailableDrug> getByPharmacy(Long pharmacyId);

	public Collection<AvailableDrug> searchByPharmacy(Long pharmacyId, DrugSearchDTO searchDTO);

	AvailableDrug addToPharmacy(Long pharmacyId, AddAvailableDrugDTO drugInfo);

	void deleteFromPharmacy(Long pharmacyId, String drugCode);

	AvailableDrug getByPharmacyAndDrug(Long pharmacyId, String drugCode);

	AvailableDrug checkIsAvailableinPharmacy(Long pharmacyId, String drugCode);

	Collection<DrugPriceDTO> getPrice(Long pharmacyId, String drugCode, Date start, Date end);

	void addPrice(Long pharmacyId, String drugCode, AddDrugPriceDTO priceInfo);

	AvailableDrug updateAmount(Long pharmacyId, String drugCode, Integer amount);

	List<PharmacyTotalPriceDTO> findPharmaciesWithRequiredDrugsAmount(List<DrugCodeAndAmountDTO> drugCodeAndAmountDTOS);

}
