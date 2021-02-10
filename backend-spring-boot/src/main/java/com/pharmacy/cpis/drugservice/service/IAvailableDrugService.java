package com.pharmacy.cpis.drugservice.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.pharmacy.cpis.drugservice.dto.*;
import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;
import com.pharmacy.cpis.pharmacyservice.dto.PharmacyTotalPriceDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;

public interface IAvailableDrugService {

	Collection<AvailableDrug> getByPharmacy(Long pharmacyId);

	public Collection<AvailableDrug> searchByPharmacy(Long pharmacyId, DrugSearchDTO searchDTO);

	AvailableDrug addToPharmacy(Long pharmacyId, AddAvailableDrugDTO drugInfo);

	void deleteFromPharmacy(Long pharmacyId, String drugCode);

	AvailableDrug getByPharmacyAndDrug(Long pharmacyId, String drugCode);

    AvailableDrug checkIsAvailableinPharmacy(Long pharmacyId, String drugCode);

    Collection<DrugPriceDTO> getPrice(Long pharmacyId, String drugCode, Date start, Date end);

	void addPrice(Long pharmacyId, String drugCode, AddDrugPriceDTO priceInfo);

	AvailableDrug updateAmount(Long pharmacyId,String drugCode,Integer amount);

	List<PharmacyTotalPriceDTO> findPharmaciesWithRequiredDrugsAmount(List<DrugCodeAndAmountDTO> drugCodeAndAmountDTOS);

}
