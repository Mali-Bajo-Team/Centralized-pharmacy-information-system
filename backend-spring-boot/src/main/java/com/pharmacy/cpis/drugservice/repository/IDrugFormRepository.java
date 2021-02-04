package com.pharmacy.cpis.drugservice.repository;

import com.pharmacy.cpis.drugservice.model.drug.DrugForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDrugFormRepository extends JpaRepository<DrugForm, Long> {
    DrugForm findByName(String name);
}
