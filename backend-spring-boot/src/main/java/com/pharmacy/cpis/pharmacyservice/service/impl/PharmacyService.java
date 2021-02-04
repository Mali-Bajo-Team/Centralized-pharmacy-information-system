package com.pharmacy.cpis.pharmacyservice.service.impl;

import com.pharmacy.cpis.pharmacyservice.dto.PharmacyRegisterDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.repository.IPharmacyRepository;
import com.pharmacy.cpis.pharmacyservice.service.IPharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacyService implements IPharmacyService {

    @Autowired
    private IPharmacyRepository pharmacyRepository;

    @Override
    public Pharmacy registerPharmacy(PharmacyRegisterDTO pharmacy) {
        return null;
    }
}
