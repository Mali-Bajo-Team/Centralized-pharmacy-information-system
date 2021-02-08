package com.pharmacy.cpis.drugservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;

public interface IAvailableDrugRepository extends JpaRepository<AvailableDrug, Long> {

	List<AvailableDrug> findByDrugCode(String drugCode);

	Optional<AvailableDrug> findByPharmacyIdAndDrugCode(Long pharmacyId, String drugCode);

}
