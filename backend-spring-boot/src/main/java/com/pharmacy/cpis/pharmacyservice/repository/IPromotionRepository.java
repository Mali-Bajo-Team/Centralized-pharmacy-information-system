package com.pharmacy.cpis.pharmacyservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.model.promotions.Promotion;

public interface IPromotionRepository extends JpaRepository<Promotion, Long> {

	List<Promotion> findByPharmacy(Pharmacy pharmacy);

}
