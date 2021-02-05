package com.pharmacy.cpis.drugservice.repository;

import com.pharmacy.cpis.drugservice.model.drug.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDrugRepository extends JpaRepository<Drug, String> {
    Boolean existsByCode(String code);
}
