package com.pharmacy.cpis.pharmacyservice.service;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;

import java.util.List;

public interface IPharmacyService {

    List<Pharmacy> findAll();
}
