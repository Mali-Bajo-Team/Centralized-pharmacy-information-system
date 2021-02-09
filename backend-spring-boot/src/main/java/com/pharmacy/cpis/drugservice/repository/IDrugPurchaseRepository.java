package com.pharmacy.cpis.drugservice.repository;

import com.pharmacy.cpis.drugservice.model.drugsales.DrugPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDrugPurchaseRepository extends JpaRepository<DrugPurchase, Long> {
}
