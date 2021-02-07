package com.pharmacy.cpis.drugservice.repository;

import com.pharmacy.cpis.drugservice.model.drug.DrugSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDrugSpecificationRepository extends JpaRepository<DrugSpecification, Long> {
    DrugSpecification findByDrugCode(String drugCode);
}
