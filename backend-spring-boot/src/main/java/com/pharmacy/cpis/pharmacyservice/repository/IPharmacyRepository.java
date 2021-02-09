package com.pharmacy.cpis.pharmacyservice.repository;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
