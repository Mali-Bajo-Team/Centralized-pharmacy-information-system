package com.pharmacy.cpis.drugservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrder;

public interface IDrugOrderRepository extends JpaRepository<DrugOrder, Long> {
	
	List<DrugOrder> findAllByPharmacyId(Long pharmacyId);
	
}
