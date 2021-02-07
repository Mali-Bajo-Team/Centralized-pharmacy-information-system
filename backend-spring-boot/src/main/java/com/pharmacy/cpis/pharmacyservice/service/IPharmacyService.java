package com.pharmacy.cpis.pharmacyservice.service;

import com.pharmacy.cpis.pharmacyservice.dto.PharmacyRegisterDTO;
import com.pharmacy.cpis.pharmacyservice.dto.PharmacyUpdateDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;

import java.util.List;

public interface IPharmacyService {

	Pharmacy getById(Long id);

	Pharmacy update(Long id, PharmacyUpdateDTO update);

	Pharmacy registerPharmacy(PharmacyRegisterDTO pharmacy);

	List<Pharmacy> findAll();

}
