package com.pharmacy.cpis.pharmacyservice.service;

import com.pharmacy.cpis.pharmacyservice.dto.PharmacyRegisterDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;

public interface IPharmacyService {

    Pharmacy registerPharmacy(PharmacyRegisterDTO pharmacy);
}
