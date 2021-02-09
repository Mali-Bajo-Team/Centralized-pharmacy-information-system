package com.pharmacy.cpis.drugservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugRequest;

public interface IDrugRequestRepository extends JpaRepository<DrugRequest, Long>  {
	
	List<DrugRequest> findByPharmacyIdOrderByTimestampDesc(Long pharmacyId);

}
