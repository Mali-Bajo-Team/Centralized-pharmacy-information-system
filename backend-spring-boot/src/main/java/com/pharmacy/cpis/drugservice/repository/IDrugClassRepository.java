package com.pharmacy.cpis.drugservice.repository;

import com.pharmacy.cpis.drugservice.model.drug.DrugClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDrugClassRepository extends JpaRepository<DrugClass, Long> {
    DrugClass findByName(String name);
}
